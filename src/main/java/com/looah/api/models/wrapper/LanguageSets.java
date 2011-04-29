/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 7, 2011 12:21:16 AM
  */
package com.looah.api.models.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("languageSets")
public class LanguageSets<T> {
    
    @XStreamAsAttribute()
    private int count;

    @XStreamImplicit(itemFieldName="languageSet")
    private List<T> languageSetList = new ArrayList<T>();

    public int getCount() {
        return count;
    }

    public LanguageSets<T> setCount() {
        this.count = languageSetList.size();
        return this;
    }

    public List<T> getLanguageSets() {
        return languageSetList;
    }

    public LanguageSets<T> setLanguageSets(List<T> languageSetList) {
        this.languageSetList = languageSetList;
        return this;
    }

    public LanguageSets<T> add(T language){
        this.languageSetList.add(language);
        return this;
    }
}
