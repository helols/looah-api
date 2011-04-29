package com.looah.api.common;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: Oct 10, 2010
 * Time: 11:07:02 AM
 * looah-api - com.looah.common
 */
@Component
public class LooahAPIConfig {

    @Value("#{looahApiProperty['looah.domain'] }")
    public String LOOAH_DOMAIN;
    @Value("#{looahApiProperty['image.util.location'] }")
    public String IMAGE_UTIL_LOCATION;

    @Value("#{looahApiProperty['article.image.base.dir'] }")
    public String ARTICLE_IMAGE_BASE_DIR ;

    @Value("#{looahApiProperty['temp.photo.base.dir'] }")
    public String TEMP_PHOTO_BASE_DIR ;

    public static final String IMAGE_BASE_URL = "img/article" ;
    
    public static final String TYPE_BLOG    = "blog";
    public static final String TYPE_PHOTO   = "photo";
    public static final String TYPE_TWITTER = "twitter";

    public static final String TYPE_LOOAH_P    = "looah";
    public static final String TYPE_TWITTER_P  = "twitter";
    public static final String TYPE_FACEBOOK_P = "facebook";
    
    public static final String DS = File.separator;
    public static final String TYPE_URL = "URL";
    public static final String TYPE_IMG = "IMAGE";

    public static final String THUMBNAIL_SIZE       = "thumbnail";
    public static final String TWICE_THUMBNAIL_SIZE = "twice_thumbnail";
    public static final String FEATURE_SIZE         = "feature";
    public static final String ORIGINAL_SIZE        = "original";

    public static final String SIZE_WIDTH_PHOTO_FEATURE   = "320";
    public static final String SIZE_HEIGHT_PHOTO_FEATURE  = "240";
    public static final String SIZE_WIDTH_PHOTO_THUMB     = "75";
    public static final String SIZE_HEIGHT_PHOTO_THUMB    = "75";
    public static final String SIZE_WIDTH_PHOTO_TW_THUMB  = "150";
    public static final String SIZE_HEIGHT_PHOTO_TW_THUMB = "150";

    public static final String KEY_ANGLE          = "__angle__";
    public static final String KEY_IMAGE_GEO_INFO = "__imageGeoInfo__";

    public static final String ROTATE_IMAGE_SUBFIX = "#R";

}