package com.daofei.service.impl;

import com.daofei.mapper.ArticleMapper;

import com.daofei.pojo.Article;

import com.daofei.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;


    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public List<Article> list() {
        return articleMapper.list();
    }

    @Override
    public List<Article> listByStatus() {
        return articleMapper.listByStatus();
    }

    @Override
    public int deleteArticleById(int id) {
        return articleMapper.deleteArticleById(id);
    }

    @Override
    public int updateStatus(Article article) {
        return articleMapper.updateStatus(article);
    }

    @Override
    public int updateLike(Article article) {
        return articleMapper.updateLike(article);
    }

    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    @Override
    public int updateHits(Article article) {
        return articleMapper.updateHits(article);
    }

    @Override
    public Article selectArticleById(int id) {
        return articleMapper.selectArticleById(id);
    }

    @Override
    public List<Article> selectArticleByHits() {
        return articleMapper.selectArticleByHits();
    }

    @Override
    public List<Article> selectArticleForCensus() {
        return articleMapper.selectArticleForCensus();
    }

    @Override
    public List<Article> selectArticleByTitleAndTime(Map map) {
        return articleMapper.selectArticleByTitleAndTime(map);
    }

    @Override
    public List<Article> selectArticleByTitle(Map map) {
        return articleMapper.selectArticleByTitle(map);
    }

    @Override
    public List<Article> selectArticleByType(int tid) {
        return articleMapper.selectArticleByType(tid);
    }

    @Override
    public Article selectArticleByAsc(int id) {
        return articleMapper.selectArticleByAsc(id);
    }

    @Override
    public Article selectArticleByDesc(int id) {
        return articleMapper.selectArticleByDesc(id);
    }

    @Override
    public List<Article> selectArticleByTag(int tagid) {
        return articleMapper.selectArticleByTag(tagid);
    }

    @Override
    public Article OneArticleById(int id) {
        return articleMapper.OneArticleById(id);
    }
}
