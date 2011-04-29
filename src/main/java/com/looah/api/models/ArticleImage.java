/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 8, 2011 12:52:05 AM
  */
package com.looah.api.models;

public class ArticleImage {

    private int articleId;
    private String fileName;
    private String type;
    private int order;
    private int thumbnail;
    
    public int getArticleId() {
        return articleId;
    }
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public int getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
