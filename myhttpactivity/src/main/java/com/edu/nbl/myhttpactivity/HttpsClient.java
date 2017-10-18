package com.edu.nbl.myhttpactivity;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 17-9-18.
 */

public class HttpsClient {
  //类的对象
  private static HttpsClient myHttps;//用来调用该类中的方法
    private static OkHttpClient client;//okhttp
//单例模式
  //第一步
  public static HttpsClient getInstance(){
    //首先判断是否为空
    if (myHttps==null){
      //初始化
      myHttps=new HttpsClient();
    }
    return new HttpsClient();
  }
  //第二步
  private HttpsClient(){
    //初始化
    client=new OkHttpClient();
  }
  //请求数据
  public Call getInfo(){
    Request mRequest=new Request
            .Builder()
            .url("")
            .build();

    return client.newCall(mRequest);
  }
}
