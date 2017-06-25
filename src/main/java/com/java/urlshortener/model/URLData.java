package com.java.urlshortener.model;

/**
 * Created by ghegde on 6/25/17.
 */
public class URLData {
    private String key;
    private String url;

    public URLData() {
    }

    public URLData(String key, String url) {
        this.key = key;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("URLData{key=");
        sb.append(key);
        sb.append(";url=");
        sb.append(url);
        sb.append("}");
        return sb.toString();
    }
}
