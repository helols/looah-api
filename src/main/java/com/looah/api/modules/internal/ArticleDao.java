/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 6, 2011 11:11:28 PM
  */
package com.looah.api.modules.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.looah.api.models.Article;
import com.looah.api.models.ArticleImage;
import com.looah.api.models.ImageGeoInfo;

public interface ArticleDao {
    
    public Integer add(Article article);

    public Integer addArticleImage(ArticleImage articleImage);

    public void addImageFile(Map<String, Object> imageMap);

    public void addTag(Map<String, Object> tagMap);

    public void addTranslatingProgresses(Map<String, Object> tpMap);

    public void addImageGeoInfo(ImageGeoInfo imgGeoInfo);

    public void addImageAttribute(Map<String, Object> imageMap);

    public HashMap<String,Object> getArticlePhoto(HashMap<String,String> paramMap);

    public List<HashMap<String, Object>> getPhotoList(HashMap<String, Object> paramMap);
}
