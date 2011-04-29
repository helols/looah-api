/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Apr 26, 2011 4:46:04 PM
  */
package com.looah.api.models;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("photo")
public class PhotoDetail {

    @XStreamOmitField
    private static final long serialVersionUID = 1L;
    
    private long id;
    
    private int langSetId;
    
    private long createdTimeStamp;

    private boolean isXcompleted;
    
    private String title;
    
    private String content;
    
    private String language;

    @XStreamAlias("image")
    private HashMap<String, Object> image;

    @XStreamAlias("user")
    private HashMap<String, Object> user;
    
    public PhotoDetail(){
    }
    
    public PhotoDetail(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isXcompleted() {
        return isXcompleted;
    }

    public void setXcompleted(boolean isXcompleted) {
        this.isXcompleted = isXcompleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getLangSetId(){
        return langSetId;
    }
    
    public void setLangSetId(int langSetId) {
        this.langSetId = langSetId;
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public long getTimeStamp() {
        return createdTimeStamp;
    }

    public HashMap<String,Object> getImage(){
        return this.image;
    }
    public void setImage(HashMap<String, Object> image) {
        this.image = image;
        
    }

    public void setUser(HashMap<String, Object> user) {
        this.user = user;
    }

    public HashMap<String, Object> getUser() {
        return user;
    }
}
