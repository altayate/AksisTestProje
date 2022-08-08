package com.example.aksistestproje.data;

import com.google.gson.annotations.SerializedName;

public class MarvelObject {
    private int code;
    private String status, copyright, attributionText, attributionHTML, etag;

    @SerializedName("data")
    private MarvelData marvelData;

    public MarvelObject(int code, String status, String copyright, String attributionText, String attributionHTML, String etag, MarvelData marvelData) {
        this.code = code;
        this.status = status;
        this.copyright = copyright;
        this.attributionText = attributionText;
        this.attributionHTML = attributionHTML;
        this.etag = etag;
        this.marvelData = marvelData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public MarvelData getMarvelData() {
        return marvelData;
    }

    public void setMarvelData(MarvelData marvelData) {
        this.marvelData = marvelData;
    }
}
