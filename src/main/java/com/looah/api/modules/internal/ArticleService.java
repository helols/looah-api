/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 7, 2011 12:35:48 AM
  */
package com.looah.api.modules.internal;

import java.util.HashMap;

import com.looah.api.models.Photo;
import com.looah.api.models.PhotoDetail;
import com.looah.api.models.wrapper.Photos;

public interface ArticleService {

    public Integer addPhoto(HashMap<String,Object> paramMap);
    
    public String getCountryInfo(String lat, String lng);
    
    public PhotoDetail get(String articleId);
    
    public Photos<Photo> getPhotoList(HashMap<String, Object> paramMap);
}
