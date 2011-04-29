/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 8, 2011 3:35:59 AM
  */
package com.looah.api.models;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("error")
public class ApiError implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private int errorCode;
    private String errorMsg;
    
    
    public ApiError(int errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    

}
