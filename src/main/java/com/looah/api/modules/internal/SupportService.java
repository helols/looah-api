/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 6, 2011 11:30:50 PM
  */
package com.looah.api.modules.internal;

import com.looah.api.models.LanguageSet;
import com.looah.api.models.wrapper.LanguageSets;

public interface SupportService {
    public LanguageSets<LanguageSet> getLanguageSetList();
}
