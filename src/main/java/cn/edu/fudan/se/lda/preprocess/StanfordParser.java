package cn.edu.fudan.se.lda.preprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class StanfordParser {
	private Analyzer analyzer;

	public StanfordParser() {
		analyzer = new StandardAnalyzer();
	}

	public List<String> preprocess(String text) {
		List<String> stemmedTokens = new ArrayList<String>();
		TokenStream stream = null;
		stream = analyzer.tokenStream(null, text);
		try {
			stream.reset();
			while (stream.incrementToken()) {
				String token = stream.getAttribute(CharTermAttribute.class).toString();
				stemmedTokens.add(lemmatize(token));
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
	
	public String lemmatize(String word){
		Properties props = new Properties(); 
        props.put("annotators", "tokenize, ssplit, pos, lemma"); 
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false); 
        Annotation document = pipeline.process(word);  
        
        CoreLabel token = document.get(SentencesAnnotation.class).get(0).get(TokensAnnotation.class).get(0);    
        String lemma = token.get(LemmaAnnotation.class);
        return lemma;
	}
}
