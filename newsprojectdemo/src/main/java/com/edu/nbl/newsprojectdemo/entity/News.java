package com.edu.nbl.newsprojectdemo.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 17-10-11.
 */

public class News implements Serializable{
    public String summary;
    public String icon;
    public String stamp;
    public String title;
    public int nid;
    public String link;
    public int type;

    public News(String summary, String icon,String stamp, String title, int nid, String link, int type) {
        this.summary = summary;
        this.icon = icon;
        this.stamp=stamp;
        this.title = title;
        this.nid = nid;
        this.link = link;
        this.type = type;
    }

    public News(String summary, String icon, String stamp, String title, int nid, String link) {
        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.nid = nid;
        this.link = link;
    }

    @Override
    public String toString() {
        return "News{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", stamp='" + stamp + '\'' +
                ", title='" + title + '\'' +
                ", nid=" + nid +
                ", link='" + link + '\'' +
                ", type=" + type +
                '}';
    }
}
