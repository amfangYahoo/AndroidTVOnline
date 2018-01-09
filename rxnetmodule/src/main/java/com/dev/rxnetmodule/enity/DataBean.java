package com.dev.rxnetmodule.enity;

import java.io.Serializable;
import java.util.List;

/**
 * author: 王可可
 * Created on 2018/1/8.
 * description: 摇摇摆摆又是一年
 */

public class DataBean implements Serializable {


    /**
     * _id : 5a4db6f0421aa90fe7253700
     * createdAt : 2018-01-04T13:09:04.283Z
     * desc : PIN 码专用输入控件，支持任意长度和输入任意数据
     * images : ["http://img.gank.io/5dfecf70-284c-4d67-9607-ee8ad1811e84"]
     * publishedAt : 2018-01-04T13:45:57.211Z
     * source : web
     * type : Android
     * url : https://github.com/nanchen2251/PinView
     * used : true
     * who : LiuShilin
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
