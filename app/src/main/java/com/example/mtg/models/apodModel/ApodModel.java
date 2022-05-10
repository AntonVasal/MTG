package com.example.mtg.models.apodModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApodModel {
    @SerializedName("copyright")
    @Expose
    private String copyright;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("explanation")
    @Expose
    private String explanation;

    @SerializedName("hdurl")
    @Expose
    private String hdUrl;

    @SerializedName("media_type")
    @Expose
    private String mediaType;

    @SerializedName("service_version")
    @Expose
    private String serviceVersion;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("url")
    @Expose
    private String url;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdUrl() {
        return hdUrl;
    }

    public void setHdUrl(String hdUrl) {
        this.hdUrl = hdUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
