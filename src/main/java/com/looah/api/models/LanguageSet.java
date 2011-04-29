/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 7, 2011 12:20:05 AM
  */
package com.looah.api.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("languageSet")
public class LanguageSet {
    private int id;
    private String name;
    
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }    
}
