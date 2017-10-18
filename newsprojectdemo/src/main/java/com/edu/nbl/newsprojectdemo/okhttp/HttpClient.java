package com.edu.nbl.newsprojectdemo.okhttp;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 17-10-11.
 */

public class HttpClient {
    private static HttpClient mHttpClient;
    private OkHttpClient mOkHttpClient;

    public HttpClient() {
        mOkHttpClient = new OkHttpClient.Builder().build();
    }
    public static synchronized HttpClient getIntance(){
        if (mHttpClient==null){
            mHttpClient= new HttpClient();
        }
        return mHttpClient;
    }
    public Call  get(){
        Request mRequest=new Request.Builder()
                .url("http://118.244.212.82:9092/newsClient/news_sort?ver=2&imei=2")
                .get()
                .build();
        Call mCall=mOkHttpClient.newCall(mRequest);
        return mCall;
    }

}
