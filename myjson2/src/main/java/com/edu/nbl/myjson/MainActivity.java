package com.edu.nbl.myjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.Rv)
    RecyclerView mRv;
private RecycleAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter=new RecycleAdapter();
        mRv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        mRv.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
            Request mRequest = new Request.Builder().url("http://newapi.meipai.com/output/channels_topics_timeline.json?id=6&page=1 ").build();
            Call mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                List<News1> mNews1List = new Gson().fromJson(result, new TypeToken<List<News1>>() {
                }.getType());
               for (int i=0;i<mNews1List.size();i++){
                   final String mCaption = mNews1List.get(i).getCaption();
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           for (int i = 0; i < 100; i++) {
                               final int finalI = i;

                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       mAdapter.add(mCaption.toString());
                                   }
                               });
                               try {
                                   Thread.sleep(1000);
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
                           }
                       }
                   }).start();
               }


            }
        });

    }
}
