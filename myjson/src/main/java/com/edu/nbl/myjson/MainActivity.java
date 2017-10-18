package com.edu.nbl.myjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
    }

    private void getData() {
        OkHttpClient mOkHttpClient=new OkHttpClient();
        Request mRequest = new Request.Builder().url("http://118.244.212.82:9092/newsClient/news_list?ver=2&subid=1&dir=2&nid=1&stamp=null&cnt=2 ").build();
        Call mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                New1 mNew1=new Gson().fromJson(result,New1.class);
                Log.e("result",mNew1.getData().toString());

//                try {
//                    JSONObject mJSONObject = new JSONObject(result);
//                    if (mJSONObject.getInt("status")==0){//请求成功
//                        JSONArray mJSONArray = mJSONObject.getJSONArray("data");
//                        for (int i=0;i<mJSONArray.length();i++){
//                            mJSONObject=mJSONArray.getJSONObject(i);
//                            String mSummary = mJSONObject.getString("summary");
//                            String mIcon = mJSONObject.getString("icon");
//                            String mStamp = mJSONObject.getString("stamp");
//                            String mTitle = mJSONObject.getString("title");
//                            String mLink = mJSONObject.getString("link");
//                            int mNid = mJSONObject.getInt("nid");
//                            int mType = mJSONObject.getInt("type");
//                            Log.e("result",mStamp+","+mIcon+","+mSummary+","+mTitle+","+mLink+","+mNid+","+mType);
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }
        });

    }
}
