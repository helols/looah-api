package com.looah.api.models.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: Oct 15, 2010
 * Time: 6:46:37 PM
 * looah-api - com.looah.model
 */
@XStreamAlias("photos")
public class Photos<T> {

    @XStreamAsAttribute()
    private int count;

    @XStreamImplicit(itemFieldName="photo")
    private List<T> photoList = new ArrayList<T>();

    public int getCount() {
        return count;
    }

    public Photos<T> setCount() {
        this.count = photoList.size();
        return this;
    }

    public List<T> getPhotos() {
        return photoList;
    }

    public Photos<T> setPhotos(List<T> photoList) {
        this.photoList = photoList;
        return this;
    }

    public Photos<T> add(T photo){
        this.photoList.add(photo);
        return this;
    }
}
