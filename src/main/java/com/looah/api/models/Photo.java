package com.looah.api.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: Oct 14, 2010
 * Time: 10:15:37 PM
 * looah-api - com.looah.model
 */
@XStreamAlias("photo")
public class Photo {
    @XStreamOmitField
    private static final long serialVersionUID = 1L;

    private long id;
    
    private long pId;
    
    private int langSetId;
    
    private long timeStamp;

    private boolean isXcompleted;
    
    private String title;
    
    private String content;
    
    private String thumbUrl;
    
    private String twiceThumbUrl;
    
    private String language;
    
    private String uname;
    
    public Photo(){
    }
    
    public Photo(long id){
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

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public void setTwiceThumbUrl(String twiceThumbUrl) {
        this.twiceThumbUrl = twiceThumbUrl;
    }

    public String getTwiceThumbUrl() {
        return twiceThumbUrl;
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

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUname() {
        return uname;
    }

    public void setPId(long pId) {
        this.pId = pId;
    }

    public long getPId() {
        return pId;
    }
}
