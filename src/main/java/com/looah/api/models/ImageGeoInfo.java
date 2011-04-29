package com.looah.api.models;


/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: Oct 11, 2010
 * Time: 4:23:47 PM
 * looah-api - com.looah.model
 */
public class ImageGeoInfo {
    private Integer id;

    private Integer degreeLatitude;
    private Integer degreeLongitude;
    private Double  minuteLatitude;
    private Double  minuteLongitude;
    private Double  secondLatitude;
    private Double  secondLongitude;

    private String latitudeRef;
    private String longitudeRef;

    private Double latitude;
    private Double longitude;
    private String county;
    private Integer articleId;
    private Integer imageId;

    private String createDate;

    private Boolean empry = true;
    
    public Boolean isEmpry() {
        return empry;
    }

    public void setEmpry(Boolean empry) {
        this.empry = empry;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDegreeLatitude() {
        return degreeLatitude;
    }

    public void setDegreeLatitude(Integer degreeLatitude) {
        this.degreeLatitude = degreeLatitude;
    }

    public Integer getDegreeLongitude() {
        return degreeLongitude;
    }

    public void setDegreeLongitude(Integer degreeLongitude) {
        this.degreeLongitude = degreeLongitude;
    }

    public Double getMinuteLatitude() {
        return minuteLatitude;
    }

    public void setMinuteLatitude(Double minuteLatitude) {
        this.minuteLatitude = minuteLatitude;
    }

    public Double getMinuteLongitude() {
        return minuteLongitude;
    }

    public void setMinuteLongitude(Double minuteLongitude) {
        this.minuteLongitude = minuteLongitude;
    }

    public Double getSecondLatitude() {
        return secondLatitude;
    }

    public void setSecondLatitude(Double secondLatitude) {
        this.secondLatitude = secondLatitude;
    }

    public Double getSecondLongitude() {
        return secondLongitude;
    }

    public void setSecondLongitude(Double secondLongitude) {
        this.secondLongitude = secondLongitude;
    }

    public String getLatitudeRef() {
        return latitudeRef;
    }

    public void setLatitudeRef(String latitudeRef) {
        this.latitudeRef = (latitudeRef != null ?latitudeRef.trim():null);
    }

    public String getLongitudeRef() {
        return longitudeRef;
    }

    public void setLongitudeRef(String longitudeRef) {
        this.longitudeRef = (longitudeRef != null ?longitudeRef.trim():null);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
