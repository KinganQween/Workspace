package com.zhuoxin.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/25 0025.
 * //成功
 {
 "code": 1,
 "msg": "添加成功！"
 }
 */
//商品上传时，网络请求返回的实体
public class GoodsUpResult {
    private int code;
    @SerializedName("msg")
    private String message;

    public GoodsUpResult() {
    }

    public GoodsUpResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

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

    @Override
    public String toString() {
        return "GoodsUpResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
