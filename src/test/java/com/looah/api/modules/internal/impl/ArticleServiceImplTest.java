/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 7, 2011 10:13:27 PM
  */
package com.looah.api.modules.internal.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.looah.api.modules.internal.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/spring/*Context.xml"})
public class ArticleServiceImplTest {
    
    @Autowired
    ArticleService articleService;
    
    @Test
    public void di(){
        Assert.assertNotNull(articleService);
    }

//    @Test
    public void getCountryInfo(){
//        System.out.println(articleService.getCountryInfo("37.34666666670000000000", "-121.93133333300000000000"));
    }
    
    @Test 
    public void getTest(){
        System.out.println(articleService.get("508"));
    }
    
}
