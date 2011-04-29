/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 6, 2011 11:11:55 PM
  */
package com.looah.api.modules.internal;

import java.util.List;

import com.looah.api.models.LanguageSet;

public interface SupportDao {

    public List<LanguageSet> getLanguageSetList();
}
