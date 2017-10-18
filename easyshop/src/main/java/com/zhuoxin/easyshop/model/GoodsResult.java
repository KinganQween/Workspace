package com.zhuoxin.easyshop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 * {
 "code": 1,
 "msg": " success",
 "datas": []
 }
 */

public class GoodsResult {
    private int code;//code为1表示成功
    @SerializedName("msg")
    private String message;//success表示成功
    private List<GoodInfo> datas;//商品列表


    public GoodsResult() {
    }

    public GoodsResult(int code, String message, List<GoodInfo> datas) {
        this.code = code;
        this.message = message;
        this.datas = datas;
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

    public List<GoodInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<GoodInfo> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "GoodsResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", datas=" + datas +
                '}';
    }
}
