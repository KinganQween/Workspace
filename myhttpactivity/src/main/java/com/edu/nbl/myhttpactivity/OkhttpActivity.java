package com.edu.nbl.myhttpactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import okhttp3.Call;

public class OkhttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        Log.e("123456","OkhttpActivity="+Thread.currentThread().getId());
        Call  mCall=HttpsClient.getInstance().getInfo();
        mCall.enqueue(new UICallBack() {
            @Override
            void onUIFailure(Call call, String e) {
                Toast.makeText(OkhttpActivity.this,e,Toast.LENGTH_SHORT).show();
            }

            @Override
            void onUIResponse(Call call, String response) {

            }
        });

    }
}
