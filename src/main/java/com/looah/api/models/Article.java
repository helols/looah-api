package com.looah.api.models;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: 2010. 10. 3
 * Time: 오전 3:27:54
 * com.looah-api - com.looah.model
 */
@XStreamAlias("article")
public class Article implements Serializable {
    @XStreamOmitField
    private static final long serialVersionUID = 1L;

    private int aId;
    private String title;
    private String content;
    private String postDate;
    private String modifiedDate;
    private String originalUrl;
    private int userId;
    private int languageSetId;
    private int categoryId;
    private String articleType;
    private int mostRead;
    private int viaId;

    public int getAId() {
        return aId;
    }

    public void setAId(int aId) {
        this.aId = aId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLanguageSetId() {
        return languageSetId;
    }

    public void setLanguageSetId(int languageSetId) {
        this.languageSetId = languageSetId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public int getMostRead() {
        return mostRead;
    }

    public void setMostRead(int mostRead) {
        this.mostRead = mostRead;
    }

    public void setViaId(int viaId) {
        this.viaId = viaId;
    }

    public int getViaId() {
        return viaId;
    }
}
