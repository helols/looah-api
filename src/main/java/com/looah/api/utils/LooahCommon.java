package com.looah.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.looah.api.common.LooahAPIConfig;

/**
 * Created by IntelliJ IDEA. User: ImYoon Date: Oct 14, 2010 Time: 11:42:50 PM
 * looah-api - com.looah.utils
 */
@Component
public class LooahCommon {
    @Autowired
    LooahAPIConfig looahAPIConfig;

    public String makeImageUrl(long imageId, String imageType) {
        return String.format("%s/image/%s/%s", looahAPIConfig.LOOAH_DOMAIN, imageId, imageType);
    }

    public String getDirSeparate(long imageId) {
        return String.format("%02d", (imageId % 1000) / 10);
    }

    public String getExtension(String contentType) {
        if (contentType == null) {
            return "";
        }
        if ((contentType.equals("image/jpg") || contentType.equals("image/jpge"))) {
            return "jpg";
        } else if (contentType.equals("image/png")) {
            return "png";
        } else if (contentType.equals("image/gif")) {
            return "gif";
        }
        return "jpg";
    }

    private static interface Patterns {
        // javascript tags and everything in between
        public static final Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);

        public static final Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
        // HTML/XML tags
        public static final Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");

        public static final Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
        // entity references
        public static final Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
        // repeated whitespace
        public static final Pattern WHITESPACE = Pattern.compile("\\s\\s+");
    }

    /**
     * Clean the HTML input.
     */
    public String clean(String s) {
        if (s == null) {
            return null;
        }

        Matcher m;

        m = Patterns.SCRIPTS.matcher(s);
        s = m.replaceAll("");
        m = Patterns.STYLE.matcher(s);
        s = m.replaceAll("");
        m = Patterns.TAGS.matcher(s);
        s = m.replaceAll("");
        m = Patterns.ENTITY_REFS.matcher(s);
        s = m.replaceAll("");
        m = Patterns.WHITESPACE.matcher(s);
        s = m.replaceAll(" ");

        return s;
    }

    public String cutText(String text, int length) {
        if(text == null || text == ""){
            return "";
        }
        if(text.length() > length){
            return text.substring(0, length-4)+" ..."; 
        }
        return text;
    }
}
