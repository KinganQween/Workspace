package com.edu.nbl.myhttpactivity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 17-9-19.
 */

public abstract class UICallBack implements Callback{
    Handler mHandler=new Handler(Looper.getMainLooper());
    @Override
    public void onFailure(final Call call, final IOException e) {
       mHandler.post(new Runnable() {
           @Override
           public void run() {
               onUIFailure(call,e.getMessage());
           }
       });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response.isSuccessful()){
                    try {
                        final String body=response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("123456","onResponse2="+Thread.currentThread().getId());
                                onUIResponse(call,body);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    abstract void onUIFailure(Call call, String e);
    abstract void onUIResponse(Call call,String response);
}
