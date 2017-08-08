/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/1/29 17:22</create-date>
 *
 * <copyright file="TestCorpus.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package cn.edu.fudan.se.ldatest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import cn.edu.fudan.se.lda.Corpus;
import cn.edu.fudan.se.lda.LdaGibbsSampler;
import cn.edu.fudan.se.lda.LdaUtil;

public class TestCorpus
{
    @Ignore
    public void testAddDocument() throws Exception
    {
        List<String> doc1 = new ArrayList<String>();
        doc1.add("hello");
        doc1.add("word");
        List<String> doc2 = new ArrayList<String>();
        doc2.add("hankcs");
        Corpus corpus = new Corpus();
        corpus.addDocument(doc1);
        corpus.addDocument(doc2);
        System.out.println(corpus);
    }
    
    @Test
    public void testAll() throws Exception
    {
        // 1. Load corpus from disk
        Corpus corpus = Corpus.load("test.txt");
        // 2. Create a LDA sampler
        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
        // 3. Train it
        ldaGibbsSampler.gibbs(3);
        // 4. The phi matrix is a LDA model, you can use LdaUtil to explain it.
        double[][] phi = ldaGibbsSampler.getPhi();
        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
        LdaUtil.explain(topicMap);
        // 5. TODO:Predict. I'm not sure whether it works, it is not stable.
        int[] document = Corpus.loadDocument("D:/javaee/lda/src/main/resources/军事_510.txt", corpus.getVocabulary());
        double[] tp = LdaGibbsSampler.inference(phi, document);
        Map<String, Double> topic = LdaUtil.translate(tp, phi, corpus.getVocabulary(), 10);
        LdaUtil.explain(topic);
    }
    
    @Test
    public void testOne() throws Exception
    {
        // 1. Load corpus from disk
    	System.out.println("start");
        Corpus corpus = Corpus.loadOneFile("1000-1050.txt");
    	System.out.println("load completed");
        // 2. Create a LDA sampler
        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
    	System.out.println("lda start");
        // 3. Train it
        ldaGibbsSampler.gibbs(3);
    	System.out.println("train completed");
    	System.out.println();
        // 4. The phi matrix is a LDA model, you can use LdaUtil to explain it.
        double[][] phi = ldaGibbsSampler.getPhi();
        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 50);
        LdaUtil.explain(topicMap);
        // 5. TODO:Predict. I'm not sure whether it works, it is not stable.
        int[] document = Corpus.loadDocument("D:/javaee/lda/src/main/resources/军事_510.txt", corpus.getVocabulary());
        double[] tp = LdaGibbsSampler.inference(phi, document);
        Map<String, Double> topic = LdaUtil.translate(tp, phi, corpus.getVocabulary(), 10);
        LdaUtil.explain(topic);
    }
}
