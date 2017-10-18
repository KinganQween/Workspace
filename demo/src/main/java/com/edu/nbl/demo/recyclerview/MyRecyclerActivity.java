package com.edu.nbl.demo.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.edu.nbl.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecyclerActivity extends AppCompatActivity {
    @BindView(R.id.rc)
    RecyclerView mRc;
    private RecycleAdapter mAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new RecycleAdapter();
//        mRc.setLayoutManager(new LinearLayoutManager(mContext));
        //        mRc.setLayoutManager(new LinearLayoutManager(this));
//        mRc.setLayoutManager(new LinearLayoutManager(this,LinearLayout.HORIZONTAL,false));
//        mRc.setLayoutManager(new GridLayoutManager(this,3));

        mRc.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayout.VERTICAL));
            mRc.setAdapter(mAdapter);
        final List<String> mList=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 100; i++) {
                        final int finalI = i;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.add("第"+ finalI +"数据");
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
        mAdapter.add(mList);
    }
}
