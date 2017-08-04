package cn.edu.fudan.se.lda.preprocess;

import java.util.LinkedList;
import java.util.List;

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
		List<Gitcommit> list = dao.selectByRepositoryScope(min, max);
		for(Gitcommit commit: list){
			List<String> l = stanfordProcessor.preprocess(commit.getMessage());
			System.out.println(commit.getMessage());
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
		List<Gitcommit> list = dao.selectByRepositoryScope(min, max);
		for(Gitcommit commit: list){
			List<String> l = preProcessor.preprocess(commit.getMessage());
			System.out.println(commit.getMessage());
			for(String s : l){
				System.out.print(s+"/");
			}
			System.out.println();
			result.add(l);
		}
		return result;
	}
}
