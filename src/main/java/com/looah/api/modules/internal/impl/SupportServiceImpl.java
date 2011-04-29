/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 6, 2011 11:32:58 PM
  */
package com.looah.api.modules.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.looah.api.models.LanguageSet;
import com.looah.api.models.wrapper.LanguageSets;
import com.looah.api.modules.internal.SupportDao;
import com.looah.api.modules.internal.SupportService;

@Service
public class SupportServiceImpl implements SupportService {

    @Autowired
    private SupportDao supportDao;

    @Override
    public LanguageSets<LanguageSet> getLanguageSetList() {
        
        LanguageSets<LanguageSet> languageSets = new LanguageSets<LanguageSet>();
        return languageSets.setLanguageSets(supportDao.getLanguageSetList()).setCount();
    }
}
