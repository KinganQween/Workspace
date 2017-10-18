package com.edu.nbl.demo.sptext;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.edu.nbl.demo.R;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        SharedPreferences sp=getSharedPreferences("天王盖地虎",MODE_PRIVATE);
        String aa=sp.getString("临","女");
        String bb=sp.getString("xx","女");
        String cc=sp.getString("dd","女");
        String dd=sp.getString("aa","女");

        Log.i("xxx",aa);
        Log.i("xxx",bb);
        Log.i("xxx",cc);
        Log.i("xxx",dd);
//        SharedPreferences.Editor mEditor=sp.edit();
//        mEditor.putString("临","零");
//        mEditor.putString("兵","一");
//        mEditor.putString("斗","二");
//        mEditor.putString("者","三");
//        mEditor.putString("皆","四");
//        mEditor.putString("阵","五");
//        mEditor.putString("列","六");
//        mEditor.commit();

    }
}
