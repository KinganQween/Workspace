package com.edu.nbl.eshop.model;

import com.google.gson.annotations.SerializedName;

/*
    "username": "xc01",
    "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
    "nickname": "666",
    "name": "yt0186129caad847a98a71189fda4f2300",
    "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
    "password": "123456"
}
 */
public class User {
    @SerializedName("username")
    private String name;//用户名
    @SerializedName("name")
    private String hx_Id;//环信id
    @SerializedName("uuid")
    private String table_Id;//用户识别码
    @SerializedName("nickname")
    private String nick_name;//用户昵称
    @SerializedName("other")
    private String head_Image;//用户头像
    @SerializedName("password")
    private String password;//用户密码
    //无参的构造方法
    public User() {
    }
    public User(String name, String hx_Id, String table_Id, String nick_name, String head_Image, String password) {
        this.name = name;
        this.hx_Id = hx_Id;
        this.table_Id = table_Id;
        this.nick_name = nick_name;
        this.head_Image = head_Image;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHx_Id() {
        return hx_Id;
    }

    public void setHx_Id(String hx_Id) {
        this.hx_Id = hx_Id;
    }

    public String getTable_Id() {
        return table_Id;
    }

    public void setTable_Id(String table_Id) {
        this.table_Id = table_Id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_Image() {
        return head_Image;
    }

    public void setHead_Image(String head_Image) {
        this.head_Image = head_Image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", hx_Id='" + hx_Id + '\'' +
                ", table_Id='" + table_Id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_Image='" + head_Image + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
