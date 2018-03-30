package cn.edu.fudan.se.lda.preprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
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
	private final List<String> STOP_LIST = Arrays.asList("a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves");
	private Analyzer analyzer;
	CharArraySet stopWords;

	Properties props; 
    
    StanfordCoreNLP pipeline; 

	public StanfordParser() {
		analyzer = new StandardAnalyzer();
		props = new Properties(); 
		props.put("annotators", "tokenize, ssplit, pos, lemma"); 
		pipeline = new StanfordCoreNLP(props, false); 
		stopWords = new CharArraySet(STOP_LIST, true);
	}

	public List<String> preprocess(String text) {
		List<String> stemmedTokens = new ArrayList<String>();
		TokenStream stream = null;
		stream = analyzer.tokenStream(null, text);

		
		stream = new StopFilter(stream, stopWords);
	    CharTermAttribute charTermAttribute = stream.addAttribute(CharTermAttribute.class);
		try {
			stream.reset();
			while (stream.incrementToken()) {
				String token = charTermAttribute.toString();
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
        Annotation document = pipeline.process(word);  
        
        CoreLabel token = document.get(SentencesAnnotation.class).get(0).get(TokensAnnotation.class).get(0);    
        String lemma = token.get(LemmaAnnotation.class);
        return lemma;
	}
}
