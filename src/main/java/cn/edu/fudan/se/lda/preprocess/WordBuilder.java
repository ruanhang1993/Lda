package cn.edu.fudan.se.lda.preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.edu.fudan.se.DataExtractor.mybatisDao.GitcommitDao;
import cn.edu.fudan.se.DataExtractor.repository.Gitcommit;

public class WordBuilder {
	private GitcommitDao dao;
	private StanfordParser stanfordProcessor;
	private Preprocessor preProcessor;
	
	public WordBuilder(){
		dao = new GitcommitDao();
		stanfordProcessor = new StanfordParser();
		preProcessor = new Preprocessor();
	}
	
	public List<List<String>> getWordInRepositoryScopeStanford(int min, int max){
		List<List<String>> result = new LinkedList<List<String>>();
		List<String> list = dao.selectMessageByRepositoryScope(min, max);
		for(String message: list){
			List<String> l = stanfordProcessor.preprocess(message);
			System.out.println(message);
			for(String s : l){
				System.out.print(s+"/");
			}
			System.out.println();
			result.add(l);
		}
		return result;
	}
	
	public List<List<String>> getWordInRepositoryScope(int min, int max){
		List<List<String>> result = new LinkedList<List<String>>();
		List<String> list = dao.selectMessageByRepositoryScope(min, max);
		for(String message: list){
			List<String> l = stanfordProcessor.preprocess(message);
			System.out.println(message);
			for(String s : l){
				System.out.print(s+"/");
			}
			System.out.println();
			result.add(l);
		}
		return result;
	}
	public List<String> loadKeyword(String filename){
		File file = new File(filename);
		BufferedReader reader = null;
		List<String> result = new ArrayList<String>();
		try {
        	reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String topicLine = reader.readLine(); 
			String[] temp = topicLine.split(" ");
			for(String s : temp){
				result.add(s);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	public void load(String filename, int min, int max)
    {
        File file = new File(filename);
        BufferedWriter br = null;
        BufferedWriter nw = null;
        List<String> keywords = this.loadKeyword("keyword.txt");
        try {
        	File tmp = File.createTempFile("tmp",null);
            tmp.deleteOnExit();
            br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            nw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("newwords.txt")), "UTF-8"));
			BufferedWriter tmpOut  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8"));
			BufferedReader tmpIn = new BufferedReader(new InputStreamReader(new FileInputStream(tmp), "UTF-8"));
			long count = 0;
			for (int i = min; i < max; i = i + 4)
	        {
				List<String> list = dao.selectMessageByRepositoryScope(i, i+4);
				for(String message: list){
					count++;
					tmpOut.newLine();
					List<String> l = stanfordProcessor.preprocess(message);
					for(String s : l){
						if(!keywords.contains(s)){
							nw.write(s);
							nw.newLine();
						}
						tmpOut.append(s+" ");
					}
				}
	        }
			tmpOut.close();
			br.append(String.valueOf(count));
			String line = null; 
			while((line = tmpIn.readLine())!=null){
				br.append(line);
				br.newLine();
			}
			tmpIn.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(nw != null){
				try {
					nw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
	public void createCData(String filename, int min, int max)
    {
        File file = new File(filename);
        BufferedWriter br = null;
        try {
        	File tmp = File.createTempFile("tmp",null);
            tmp.deleteOnExit();
			br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			BufferedWriter tmpOut  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8"));
			BufferedReader tmpIn = new BufferedReader(new InputStreamReader(new FileInputStream(tmp), "UTF-8"));
			for (int i = min; i < max; i = i + 4)
	        {
				List<String> list = dao.selectMessageByRepositoryScope(i, i+4);
				for(String message: list){
					List<String> l = stanfordProcessor.preprocess(message);
					Map<String, Integer> map = new HashMap<String, Integer>();
					for(String s : l){
						if(map.get(s)==null){
							map.put(s, 1);
						}else{
							Integer temp = map.get(s);
							map.replace(s, temp+1);
						}
					}
					for(String s: map.keySet()){
						tmpOut.append(s+":"+map.get(s)+" ");
					}
					tmpOut.newLine();
				}
	        }
			tmpOut.close();
			String line = null; 
			while((line = tmpIn.readLine())!=null){
				br.append(line);
				br.newLine();
			}
			tmpIn.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
	
	public void createGoogleData(String filename, int min, int max)
    {
        File file = new File(filename);
        BufferedWriter br = null;
        try {
        	File tmp = File.createTempFile("tmp",null);
            tmp.deleteOnExit();
			br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			BufferedWriter tmpOut  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8"));
			BufferedReader tmpIn = new BufferedReader(new InputStreamReader(new FileInputStream(tmp), "UTF-8"));
			long count = 0;
			for (int i = min; i < max; i = i + 4)
	        {
				List<String> list = dao.selectMessageByRepositoryScope(i, i+4);
				for(String message: list){
					List<String> l = stanfordProcessor.preprocess(message);
					Map<String, Integer> map = new HashMap<String, Integer>();
					for(String s : l){
						if(map.get(s)==null){
							map.put(s, 1);
						}else{
							Integer temp = map.get(s);
							map.replace(s, temp+1);
						}
					}
					tmpOut.append("DOCID=:"+count+"	");
					for(String s: map.keySet()){
						tmpOut.append(s+" "+map.get(s)+" ");
					}
					tmpOut.newLine();
					count++;
				}
	        }
			tmpOut.close();
			String line = null; 
			while((line = tmpIn.readLine())!=null){
				br.append(line);
				br.newLine();
			}
			tmpIn.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
	public void getNewWords(String wordIndex){
        List<String> keywords = this.loadKeyword("keyword.txt");
        BufferedReader br = null;
        BufferedWriter wr = null;
        try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(wordIndex)), "UTF-8"));
			wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("newnewwords.txt")), "UTF-8"));
			String line = null; 
			while((line = br.readLine())!=null){
				String word = line.split(" ")[1];
				if(!keywords.contains(word)){
					wr.write(word);
					wr.newLine();
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(wr != null){
				try {
					wr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void createFinalData(String filename, int min, int max)
    {
        File file = new File(filename);
        BufferedWriter br = null;
        try {
        	File tmp = File.createTempFile("tmp",null);
            tmp.deleteOnExit();
			br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			BufferedWriter tmpOut  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8"));
			BufferedReader tmpIn = new BufferedReader(new InputStreamReader(new FileInputStream(tmp), "UTF-8"));
			for (int i = min; i < max; i = i + 4)
	        {
				List<Gitcommit> list = dao.selectByRepositoryScope(i, i+4);
				for(Gitcommit commit: list){
					List<String> l = stanfordProcessor.preprocess(commit.getMessage());
					Map<String, Integer> map = new HashMap<String, Integer>();
					for(String s : l){
						if(map.get(s)==null){
							map.put(s, 1);
						}else{
							Integer temp = map.get(s);
							map.replace(s, temp+1);
						}
					}
					tmpOut.append("DOCID=:"+commit.getCommitId()+"	");
					for(String s: map.keySet()){
						tmpOut.append(s+" "+map.get(s)+" ");
					}
					tmpOut.newLine();
				}
	        }
			tmpOut.close();
			String line = null; 
			while((line = tmpIn.readLine())!=null){
				br.append(line);
				br.newLine();
			}
			tmpIn.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
