/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/1/29 17:08</create-date>
 *
 * <copyright file="Vocabulary.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package cn.edu.fudan.se.lda;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary
{
    List<String> words;

    public Vocabulary()
    {
        words = new ArrayList<String>();
    }

    public Integer getId(String word)
    {
        return getId(word, false);
    }

    public String getWord(int id)
    {
        return words.get(id);
    }

    public Integer getId(String word, boolean create)
    {
        Integer id = words.indexOf(word);
        if (!create) return id;
        if (id == -1)
        {
            id = words.size();
            words.add(word);
        }
        return id;
    }

    public int size()
    {
        return words.size();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++)
        {
            sb.append(i).append("=").append(words.get(i)).append("\n");
        }
        return sb.toString();
    }
}
