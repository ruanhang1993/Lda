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
}
