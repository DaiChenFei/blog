package com.daofei.service;

import com.daofei.pojo.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public int addArticle(Article article);
    List<Article> list();
    List<Article> listByStatus();
    public int deleteArticleById(int id);
    public int updateStatus(Article article);
    public int updateLike(Article article);
    public int updateArticle(Article article);
    public int updateHits(Article article);
    public Article selectArticleById(int id);
    public List<Article> selectArticleByHits();
    public List<Article> selectArticleForCensus();
    List<Article> selectArticleByTitleAndTime(Map map);
    List<Article> selectArticleByTitle(Map map);
    List<Article> selectArticleByType(int tid);
    public Article selectArticleByAsc(int id);
    public Article selectArticleByDesc(int id);
    public List<Article> selectArticleByTag(int tagid);
    public  Article OneArticleById(int id);
}
