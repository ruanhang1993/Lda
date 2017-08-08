package cn.edu.fudan.se.ldatest;

import java.util.List;

import org.junit.Test;

import cn.edu.fudan.se.lda.preprocess.StanfordParser;
import cn.edu.fudan.se.lda.preprocess.WordBuilder;

public class DbTest {
	@Test
	public void testSnowball(){
		WordBuilder wb = new WordBuilder();
		List<List<String>> result = wb.getWordInRepositoryScope(1000, 1010);
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
		wb.load("1000-1050.txt", 1000, 1050);
	}
	
	@Test
	public void testCLoad(){
		WordBuilder wb = new WordBuilder();
		wb.createCData("pyteststanford", 1000, 1080);
	}
	
	@Test
	public void testGLoad(){
		WordBuilder wb = new WordBuilder();
		wb.createGoogleData("ctrainstanford.dat", 1000, 1080);
	}
}
