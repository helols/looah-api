package com.looah.api.modules;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.looah.api.models.ApiError;
import com.looah.api.models.UploadPhoto;
import com.looah.api.models.UploadPhotoResult;
import com.looah.api.modules.internal.ArticleService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: Oct 15, 2010
 * Time: 1:44:04 AM
 * looah-api - com.looah.web
 */
@Controller
@RequestMapping("/photo")
public class PhotoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ArticleService articleService;
    
    @RequestMapping(value="/list",method=GET)
    public ModelAndView list(@RequestParam(required = false, defaultValue = "0") int page,
                             @RequestParam(required = false, defaultValue = "20") int count,
                             @RequestParam(required = false, defaultValue = "") String uname,
                             @RequestParam(required = false, defaultValue = "0") int isXCompleted,
                             @RequestParam(required = false, defaultValue = "0") int sinceId,
                             @RequestParam(required = false, defaultValue = "0") int lastTimeStamp,
                             @RequestParam(required = false, defaultValue = "0") int langSetId){
        HashMap<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("skiprows", (page*count));
        paramMap.put("pagesize", (count > 20 || count == 0 ?20:count));
        paramMap.put("uname", uname);
        paramMap.put("isXCompleted", isXCompleted);
        paramMap.put("langSetId", langSetId);
        paramMap.put("sinceId", sinceId);
        paramMap.put("lastTimeStamp", lastTimeStamp);
        System.out.println(paramMap);
        logger.debug("paramMap",paramMap);
        return new ModelAndView().addObject("data",articleService.getPhotoList(paramMap));
    }

    @RequestMapping(value="add",method= RequestMethod.POST)
    public ModelAndView add(UploadPhoto uploadPhoto){
        if(uploadPhoto.getPhoto() == null || uploadPhoto.getLanguageSetId() == 0){
            return new ModelAndView().addObject("data", new ApiError(-1,"You must need required field value."));
        }

        HashMap<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("note",uploadPhoto.getNote());
        paramMap.put("tags",uploadPhoto.getTags());
        paramMap.put("languageSetId",uploadPhoto.getLanguageSetId());
        paramMap.put("userKey",7);

        paramMap.put("photo", uploadPhoto.getPhoto());
        paramMap.put("location", ((DiskFileItem)uploadPhoto.getPhoto().getFileItem()).getStoreLocation().getAbsolutePath());
        paramMap.put("fileName",uploadPhoto.getPhoto().getOriginalFilename());
        paramMap.put("contentType",uploadPhoto.getPhoto().getContentType());

        int articleId = articleService.addPhoto(paramMap);
        if(articleId > 0){
            UploadPhotoResult uploadPhotoResult = new UploadPhotoResult();
            uploadPhotoResult.setArticleId(articleId);
            uploadPhotoResult.setCode(0);
            uploadPhotoResult.setMsg("OK");
            return new ModelAndView().addObject("data",uploadPhotoResult);
        }else{
            return new ModelAndView().addObject("data", new ApiError(-1,"error.."));
        }
    }
    
    @RequestMapping(value="/get/{id}", method= RequestMethod.GET)
    public ModelAndView get(@PathVariable String id){
        
        Pattern patt = Pattern.compile("[0-9]+");
        Matcher m = patt.matcher(id);
        if(m.matches()){
            return new ModelAndView().addObject("data",articleService.get(id));
        }
        return new ModelAndView().addObject("data",new ApiError(-1,"error.."));
    }
}
