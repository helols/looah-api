/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 6, 2011 11:17:32 PM
  */
package com.looah.api.modules.internal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.looah.api.models.LanguageSet;
import com.looah.api.modules.internal.SupportDao;

@Repository
public class SupportDaoImpl implements SupportDao {

    @Autowired
    SqlMapClientTemplate template;

    @Override
    public List<LanguageSet> getLanguageSetList() {
        return (List<LanguageSet>)template.queryForList("support.getLanguageSetList");
    }
    
    
}
