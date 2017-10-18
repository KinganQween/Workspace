package com.zhuoxin.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/19 0019.
  "code": 1,
 "msg": "succeed",
 "data": {}
 */

public class UserResult {
    private int code;
    @SerializedName("msg")
    private String message;
    private User data;

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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
