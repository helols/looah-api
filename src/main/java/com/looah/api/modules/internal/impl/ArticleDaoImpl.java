/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 6, 2011 11:12:18 PM
  */
package com.looah.api.modules.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.looah.api.models.Article;
import com.looah.api.models.ArticleImage;
import com.looah.api.models.ImageGeoInfo;
import com.looah.api.modules.internal.ArticleDao;

@Repository
public class ArticleDaoImpl implements ArticleDao {

    @Autowired
    SqlMapClientTemplate template;

    @Override
    public Integer add(Article article) {
        return (Integer) template.insert("article.add",article);
    }

    @Override
    public Integer addArticleImage(ArticleImage articleImage) {
        return (Integer) template.insert("article.addArticleImage",articleImage);
    }

    @Override
    public void addImageFile(Map<String, Object> imageMap) {
        template.insert("article.addImageFile",imageMap);
    }

    @Override
    public void addTag(Map<String, Object> tagMap) {
        template.insert("article.addTag",tagMap);
        
    }

    @Override
    public void addTranslatingProgresses(Map<String, Object> tpMap) {
        template.insert("article.addTranslatingProgresses",tpMap);
    }

    @Override
    public void addImageGeoInfo(ImageGeoInfo imgGeoInfo) {
        template.insert("article.addImageGeoInfo",imgGeoInfo);
    }

    @Override
    public void addImageAttribute(Map<String, Object> imageMap) {
        template.insert("article.addImageAttribute",imageMap);
    }

    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String,Object> getArticlePhoto(HashMap<String,String> paramMap){
        return (HashMap<String, Object>) template.queryForObject("article.getArticlePhoto", paramMap);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<HashMap<String, Object>>getPhotoList(HashMap<String, Object> paramMap) {
        return template.queryForList("article.getPhotoList", paramMap);
    }
}
