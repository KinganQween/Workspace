package com.zhuoxin.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/18 0018.
 GoodsDetailResult
 "code": 1,
 "msg": " success",
 "datas": []
 "user":{}
 商品详情的实体类
 */

public class GoodsDetailResult {
    private int code;//响应数据里的响应码  成功为1
    @SerializedName("msg")
    private String message;
    private GoodsDetail datas;
    //用户信息
    private User user;
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

    public GoodsDetail getDatas() {
        return datas;
    }

    public void setDatas(GoodsDetail datas) {
        this.datas = datas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GoodsDetailResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", datas=" + datas +
                ", user=" + user +
                '}';
    }
}
