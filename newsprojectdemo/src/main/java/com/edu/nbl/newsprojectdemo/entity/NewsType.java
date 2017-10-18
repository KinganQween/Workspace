package com.edu.nbl.newsprojectdemo.entity;

/**
 * Created by Administrator on 17-10-11.
 */

public class NewsType {
    public String subgroup;
    public int subid;

    public NewsType(String subgroup, int subid) {
        this.subgroup = subgroup;
        this.subid = subid;
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "subgroup='" + subgroup + '\'' +
                ", subid=" + subid +
                '}';
    }
}
