package com.looah.api.models;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: Nov 11, 2010
 * Time: 9:26:56 PM
 * looah-api - com.looah.model
 */
public class UploadPhoto {
    private CommonsMultipartFile photo;
    private int languageSetId;
    private String note;
    private String tags;

    public CommonsMultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(CommonsMultipartFile photo) {
        this.photo = photo;
    }

    public int getLanguageSetId() {
        return languageSetId;
    }

    public void setLanguageSetId(int languageSetId) {
        this.languageSetId = languageSetId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
