package cn.edu.fudan.se.lda.preprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

public class Preprocessor {
	private Analyzer analyzer;
	private SnowballStemmer stemmer;

	public Preprocessor() {
		analyzer = new StandardAnalyzer();
		stemmer = new englishStemmer();
	}

//	public static void main(String[] args) {
//		Preprocessor preprocessor = new Preprocessor();
//		String text = "";
//		List<String> stemmedTokens = preprocessor.preprocess(text);
//		System.out.println(stemmedTokens);
//	}

	public List<String> preprocess(String text) {
		List<String> stemmedTokens = new ArrayList<String>();
		TokenStream stream = null;
		stream = analyzer.tokenStream(null, text);
		try {
			stream.reset();
			while (stream.incrementToken()) {
				String token = stream.getAttribute(CharTermAttribute.class).toString();
				stemmer.setCurrent(token);
				stemmer.stem();
				String stemmed = stemmer.getCurrent();
				stemmedTokens.add(stemmed);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stemmedTokens;
	}
}
