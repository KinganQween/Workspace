package com.edu.nbl.eshop.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public abstract class UICallBack implements Callback{
    //拿到主线程的handler Looper也是主线程的
    Handler handler=new Handler(Looper.getMainLooper());
    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {//此方法在主线程里执行
                onFailureUI(call,e);
            }
        });
    }
    public abstract void onFailureUI(Call call,IOException e);//此回调方法执行在主线程中
    @Override
    public void onResponse(final Call call, Response response) throws IOException {
        //判断响应成功
        if (!response.isSuccessful()){//失败
            throw new IOException("error code:"+response.code());//例如code=404
        }
        final String json=response.body().string();//将响应变成字符串
        handler.post(new Runnable() {
            @Override
            public void run() {
                onResponseUI(call,json);//此方法在主线程里回调
            }
        });
    }
    public abstract void onResponseUI(Call call,String body);
}
