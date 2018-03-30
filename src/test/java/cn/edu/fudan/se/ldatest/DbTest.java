package cn.edu.fudan.se.ldatest;

import java.util.List;

import org.junit.Test;

import cn.edu.fudan.se.lda.preprocess.StanfordParser;
import cn.edu.fudan.se.lda.preprocess.WordBuilder;
import cn.edu.se.lda.store.DbSaver;

public class DbTest {
	@Test
	public void testSnowball(){
		WordBuilder wb = new WordBuilder();
		List<List<String>> result = wb.getWordInRepositoryScope(1000, 1010);
	}
	
	@Test
	public void testPreprocess(){
		List<String> l = (new StanfordParser()).preprocess("I am testing the bug report");
		for(String s : l)
			System.out.println(s);
	}

	@Test
	public void testStanford(){
		WordBuilder wb = new WordBuilder();
		List<List<String>> result = wb.getWordInRepositoryScopeStanford(1000, 1010);
	}
	
	@Test
	public void testLemmatize(){
		StanfordParser wb = new StanfordParser();
		System.out.println(wb.lemmatize("tells"));
	}
	
	@Test
	public void testLoad(){
		WordBuilder wb = new WordBuilder();
//		wb.load("test.txt", 736, 3075);
		wb.load("1000-1250.txt", 1000, 1250);
	}
	
	@Test
	public void testCLoad(){
		WordBuilder wb = new WordBuilder();
		wb.createCData("pyteststanford", 1000, 1080);
	}
	
	@Test
	public void testLoadKeyword(){
		WordBuilder wb = new WordBuilder();
		List<String> l = wb.loadKeyword("keyword.txt");
		for(String s : l){
			System.out.println(s);
		}
	}
	
	@Test
	public void testGLoad(){
		WordBuilder wb = new WordBuilder();
		wb.createGoogleData("1000_1250_new.dat", 1000, 1250);
	}
	
	@Test
	public void testFinalData(){
		WordBuilder wb = new WordBuilder();
		wb.createFinalData("testused.dat", 736, 3075);
	}
	
	@Test
	public void testSave(){
		DbSaver wb = new DbSaver("new_450116001_rule.txt");
		wb.saveToDb("testused.dat", "new_450116001_test.txt");
	}
	@Test
	public void testSkip(){
		DbSaver wb = new DbSaver();
		wb.saveToDbSkip("final.dat", "inference_result_final.txt", 778000);
	}
	@Test
	public void testNewWord(){
		WordBuilder wb = new WordBuilder();
		wb.getNewWords("newwordindex.txt");
	}
	@Test
	public void testLoadTopic(){
		DbSaver wb = new DbSaver();
		wb.loadTopicValue("inference_result_rule.txt");
	}
}
