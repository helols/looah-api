/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 7, 2011 12:36:59 AM
  */
package com.looah.api.modules.internal.impl;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.looah.api.common.LooahAPIConfig;
import com.looah.api.models.Article;
import com.looah.api.models.ArticleImage;
import com.looah.api.models.ImageGeoInfo;
import com.looah.api.models.Photo;
import com.looah.api.models.PhotoDetail;
import com.looah.api.models.wrapper.Photos;
import com.looah.api.modules.internal.ArticleDao;
import com.looah.api.modules.internal.ArticleService;
import com.looah.api.utils.ImageUtil;
import com.looah.api.utils.LooahCommon;

import static com.looah.api.common.LooahAPIConfig.FEATURE_SIZE;
import static com.looah.api.common.LooahAPIConfig.KEY_IMAGE_GEO_INFO;
import static com.looah.api.common.LooahAPIConfig.ORIGINAL_SIZE;
import static com.looah.api.common.LooahAPIConfig.THUMBNAIL_SIZE;
import static com.looah.api.common.LooahAPIConfig.TWICE_THUMBNAIL_SIZE;
import static com.looah.api.common.LooahAPIConfig.TYPE_IMG;
import static com.looah.api.common.LooahAPIConfig.TYPE_LOOAH_P;
import static com.looah.api.common.LooahAPIConfig.TYPE_PHOTO;

@Service
public class ArticleServiceImpl implements ArticleService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    ArticleDao articleDao;
    
    @Autowired
    ImageUtil imageUtil;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    LooahAPIConfig looahAPIConfig;
    
    @Autowired
    LooahCommon looahCommon;
    
    @Override
    @Transactional
    public Integer addPhoto(HashMap<String,Object> paramMap) {
        Map<String,Object> metaData = imageUtil.saveImageFile((String)paramMap.get("location"));

        byte[] imgBytes = null;
        if((Boolean)metaData.get("isRotate")){
            InputStream in = null;
            try {
                in = new FileInputStream ((String)paramMap.get("location"));  
                imgBytes = new byte[in.available()];  
                in.read (imgBytes);  
            } catch (Exception e) {
                logger.error(e.getMessage());
            }finally{
                try {
                    in.close ();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }else{
            imgBytes =  ((CommonsMultipartFile)paramMap.get("photo")).getBytes();
        }
        
        if(imgBytes != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = format.format(new GregorianCalendar().getTime());
            if(paramMap.get("note") != null){
                paramMap.put("note",((String)paramMap.get("note")).trim());
            }
            Article article = new Article();
            article.setLanguageSetId((Integer)paramMap.get("languageSetId"));
            article.setContent(looahCommon.clean((String)paramMap.get("note")));
            article.setArticleType(TYPE_PHOTO);
            article.setCategoryId(99);
            article.setMostRead(0);
            article.setViaId(2);
            article.setOriginalUrl("");
            article.setTitle("");
            article.setUserId(42);
            article.setPostDate(createTime);
            article.setModifiedDate(createTime);
            
            int contentLength = 1;
            
            if(article.getContent() != null && !article.getContent().equals("")){
                contentLength++;
            }

            int articleId = articleDao.add(article);
            
            /* article_images table insert */
            ArticleImage articleImage = new ArticleImage();
            articleImage.setArticleId(articleId);
            articleImage.setFileName(paramMap.get("fileName").toString());
            articleImage.setOrder(0);
            articleImage.setThumbnail(1);
            articleImage.setType(TYPE_IMG);
            Integer imageId = articleDao.addArticleImage(articleImage);
            
            /* images table insert */
            Map<String,Object> imageMap = new HashMap<String,Object>();
            imageMap.put("id",imageId);
            imageMap.put("body",imgBytes);
            articleDao.addImageFile(imageMap);
            
            /* image_attributes table insert */
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage((String)paramMap.get("location")));
            imageMap.remove("body");
            imageMap.put("type", paramMap.get("contentType"));
            imageMap.put("length", imgBytes.length);
            imageMap.put("width", icon.getIconWidth());
            imageMap.put("height", icon.getIconHeight());
            imageMap.put("bits", 0);
            imageMap.put("channels", 0);
            
            articleDao.addImageAttribute(imageMap);
            
            /* tags table insert */
            if(paramMap.get("tags") != null && !paramMap.get("tags").toString().trim().equals("")){
                List<String> alreadyTags = new ArrayList<String>();
                String[] tags = paramMap.get("tags").toString().trim().split(",");
                Map<String,Object> tagMap = new HashMap<String,Object>();
                tagMap.put("articleId", articleId);
                for (String tag : tags) {
                    if(!tag.trim().equals("") && !tag.trim().equals(",") && !alreadyTags.contains(tag)){
                        tagMap.put("name",tag.trim());
                        articleDao.addTag(tagMap);
                        alreadyTags.add(tag);
                    }
                }
            }
            /* translating_progresses table insert */
            Map<String,Object> tpMap = new HashMap<String,Object>();
            tpMap.put("articleId",articleId);
            tpMap.put("originalCount", contentLength);
            tpMap.put("translatedCount", 0);
            tpMap.put("updatedDate", createTime);
            articleDao.addTranslatingProgresses(tpMap);
            
            if(metaData.get(KEY_IMAGE_GEO_INFO) != null){
                ImageGeoInfo imgGeoInfo = (ImageGeoInfo)metaData.get(KEY_IMAGE_GEO_INFO);
                imgGeoInfo.setCounty(this.getCountryInfo(imgGeoInfo.getLatitude().toString(), imgGeoInfo.getLongitude().toString()));
                imgGeoInfo.setCreateDate(createTime);
                imgGeoInfo.setArticleId(articleId);
                imgGeoInfo.setImageId(imageId);
                articleDao.addImageGeoInfo(imgGeoInfo);
            }
            
            return articleId;
        }
        return 0;
    }

    public String getCountryInfo(String lat, String lng){
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("lat", lat);
        vars.put("lng", lng);
        ResponseEntity<HashMap> response = restTemplate.getForEntity("http://maps.google.com/maps/api/geocode/json?latlng={lat},{lng}&sensor=false", HashMap.class, vars);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            List<HashMap<String,Object>> addr = (List)((HashMap)((List)response.getBody().get("results")).get(0)).get("address_components");
            for (HashMap<String, Object> hashMap : addr) {
                if(((List)hashMap.get("types")).contains("country")){
                    return hashMap.get("long_name").toString();
                }
            }
            return "";
        }else{
            return "";
        }
    }

    @Override
    public PhotoDetail get(String id) {
        HashMap<String, String> paramMap = new HashMap<String,String>();
        paramMap.put("id", id);
        paramMap.put("type", TYPE_PHOTO);
        HashMap<String,Object> articlePhotoInfo = articleDao.getArticlePhoto(paramMap);
        if(articlePhotoInfo != null && !articlePhotoInfo.isEmpty()){
            PhotoDetail photoDetail = new PhotoDetail((Long) articlePhotoInfo.get("id"));

            /* set user infos */
            HashMap<String,Object> userInfo = new HashMap<String,Object>();
            userInfo.put("uId", articlePhotoInfo.get("uId"));
            userInfo.put("uname", articlePhotoInfo.get("uname"));
            userInfo.put("profileUrl", looahAPIConfig.LOOAH_DOMAIN+"/img/default-profile.png");
            
            if(TYPE_LOOAH_P.equals(articlePhotoInfo.get("profileType").toString())){
                if(articlePhotoInfo.get("profileUrl") != null && !"".equals(articlePhotoInfo.get("profileUrl").toString())){
                    userInfo.put("profileUrl", looahAPIConfig.LOOAH_DOMAIN+"/img/user_img/"+
                                               looahCommon.getDirSeparate((Long) articlePhotoInfo.get("uId"))+
                                               "/"+
                                               articlePhotoInfo.get("profileUrl"));
                }
            }else{
                if(articlePhotoInfo.get("thirdProfileImg") != null && !"".equals(articlePhotoInfo.get("thirdProfileImg").toString())){
                    userInfo.put("profileUrl", articlePhotoInfo.get("thirdProfileImg"));
                }
            }
            
            /* set image info */
            HashMap<String,Object> imageInfo = new HashMap<String,Object>();
            long    imageId = (Long) articlePhotoInfo.get("pId");
            String contentType = (String) articlePhotoInfo.get("contentType");
            
            imageInfo.put("pId", imageId);
            imageInfo.put("width", articlePhotoInfo.get("width"));
            imageInfo.put("height", articlePhotoInfo.get("height"));
            imageInfo.put("contentType", contentType);
            imageInfo.put("originalUrl", looahCommon.makeImageUrl(imageId, ORIGINAL_SIZE));
            imageInfo.put("featureUrl",  looahCommon.makeImageUrl(imageId, FEATURE_SIZE));
            imageInfo.put("thumbnailUrl", looahCommon.makeImageUrl(imageId, THUMBNAIL_SIZE));
            imageInfo.put("twiceThumbnailUrl", looahCommon.makeImageUrl(imageId, TWICE_THUMBNAIL_SIZE));

            photoDetail.setId((Long)articlePhotoInfo.get("id"));
            photoDetail.setLanguage(articlePhotoInfo.get("language").toString());
            photoDetail.setLangSetId((Integer)articlePhotoInfo.get("langSetId"));
            photoDetail.setTitle(articlePhotoInfo.get("title").toString());
            photoDetail.setCreatedTimeStamp((Long)articlePhotoInfo.get("createdTimeStamp"));
            photoDetail.setXcompleted(articlePhotoInfo.get("xCompleted").equals(1));
            String content = articlePhotoInfo.get("content").toString(); 
            if(articlePhotoInfo.get("translateContent") != null){
              content = articlePhotoInfo.get("translateContent").toString();
            }
            photoDetail.setContent(content);
            photoDetail.setImage(imageInfo);
            photoDetail.setUser(userInfo);
            return photoDetail; 
        }
        return new PhotoDetail(0);
    }

    @Override
    public Photos<Photo> getPhotoList(HashMap<String, Object> paramMap) {
        List<HashMap<String, Object>> results = articleDao.getPhotoList(paramMap);
        Photos<Photo> photos = new Photos<Photo>(); 
        String content = "";
        if(!results.isEmpty()){
            for (HashMap<String, Object> result:results) {
                Photo photo = new Photo();
                photo.setId((Long)result.get("id"));
                photo.setPId((Long)result.get("pId"));
                photo.setLanguage(result.get("language").toString());
                photo.setLangSetId((Integer)result.get("langSetId"));
                photo.setUname(result.get("uname").toString());
                photo.setTitle(result.get("title").toString());
                photo.setTimeStamp((Long)result.get("timestamp"));
                photo.setThumbUrl(looahCommon.makeImageUrl((Long)result.get("pId"), LooahAPIConfig.THUMBNAIL_SIZE));
                photo.setTwiceThumbUrl(looahCommon.makeImageUrl((Long)result.get("pId"), LooahAPIConfig.TWICE_THUMBNAIL_SIZE));
                photo.setXcompleted(result.get("xCompleted").equals(1));
                content = result.get("content").toString(); 
                if(result.get("translateContent") != null){
                  content = result.get("translateContent").toString();
                }
                photo.setContent(looahCommon.cutText(content,50));
                photos.add(photo);
            }
        }
        return photos.setCount();
    }
}
