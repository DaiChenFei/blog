package com.daofei.mapper;

import com.daofei.pojo.Article;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    public int addArticle(Article article);
    public List<Article> list();
    public List<Article> listByStatus();
    public int deleteArticleById(int id);
    public  Article selectArticleById(int id);
    public  List<Article> selectArticleByHits();
    public  List<Article> selectArticleForCensus();
    public int updateStatus(Article article);
    public int updateArticle(Article article);
    public int updateLike(Article article);
    public int updateHits(Article article);
    public List<Article> selectArticleByTitleAndTime(Map map);
    public List<Article> selectArticleByTitle(Map map);
    public List<Article> selectArticleByType(int lx);
    public Article selectArticleByAsc(int id);
    public Article selectArticleByDesc(int id);
    public List<Article> selectArticleByTag(int tagid);
    public  Article OneArticleById(int id);

}
