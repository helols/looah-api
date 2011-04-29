/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 8, 2011 3:32:04 AM
  */
package com.looah.api.models;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("result")
public class UploadPhotoResult implements Serializable{

    @XStreamOmitField
    private static final long serialVersionUID = 1L;
    
    private int code;
    private String msg;
    private int articleId;
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    
    public int getArticleId() {
        return articleId;
    }
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
}
