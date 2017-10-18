package com.zhuoxin.easyshop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/27 0027.
 * 好友列表的实体类
 * "code": 1,
 "msg":"success",
 "datas": [
 {
 "uuid": "9BEEBFFFC14545CDAB517210D46A7923",             //用户表中主键
 "username": "renxiang",                                 //用户名
 "name": "yt1cd3ce4132a746db8a1a8f1c0b34f907",           //环信id
 "other": "/images/9BEEBFFFC14545CDAB517210D46A7923/A0D46D81C8.jpg",  //用户头像
 "nickname": "android"                   //用户昵称
 }，
 ]
 */
public class GetUsersResult {
    private int code;
    @SerializedName("msg")
    private String message;
    private List<User> datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "GetUsersResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", datas=" + datas +
                '}';
    }
}
