package com.edu.nbl.newsprojectdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.adapter.NewsFragmentAdapter;
import com.edu.nbl.newsprojectdemo.adapter.NewsFragmentContentAdapter;
import com.edu.nbl.newsprojectdemo.biz.UrlUtils;
import com.edu.nbl.newsprojectdemo.entity.News;
import com.edu.nbl.newsprojectdemo.entity.NewsType;
import com.edu.nbl.newsprojectdemo.okhttp.HttpClient;
import com.edu.nbl.newsprojectdemo.utils.LoadMoreForRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 17-10-10.
 */

public class NewsFragment extends Fragment {


    @BindView(R.id.news_title)
    RecyclerView mNewsTitle;
    @BindView(R.id.news_news)
    RecyclerView mNewsNews;
    Unbinder unbinder;
    @BindView(R.id.nf_srl)
    SwipeRefreshLayout mNfSrl;
    private List<NewsType> mNewsTypeList = new ArrayList<>();
    private NewsFragmentAdapter mAdapter;
    private NewsFragmentContentAdapter mContentAdapter;
    private Context mContext;
    List<News> mNewsList = new ArrayList<News>();
    List<News> mNewsList2 = new ArrayList<News>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new LoadMoreForRecyclerView(mNewsNews,new LoadMoreForRecyclerView.LoadMoreListener() {
            @Override
            public void loadMore() {
                data(mAdapter.danji,2,mNewsList.get(mNewsNews.getAdapter().getItemCount()-1).nid);
            }
        },getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, mView);
        mAdapter = new NewsFragmentAdapter(getActivity(), mNewsTypeList, new NewsFragmentAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(NewsType newsType, int a) {
                mAdapter.danji = a;
                mNewsList.clear();
                data(newsType.subid, 1, 0);
                mAdapter.notifyDataSetChanged();
            }
        });
        mNewsTitle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        mNewsTitle.setAdapter(mAdapter);
        init();
        mContentAdapter = new NewsFragmentContentAdapter(getActivity(), mNewsList);
        mNewsNews.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mNewsNews.setAdapter(mContentAdapter);
        mNfSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mContentAdapter.notifyDataSetChanged();
                mNfSrl.setRefreshing(false);
//                //将第二个集合设置给适配器
//                mContentAdapter.setNewsList(mNewsList2);
//                //下拉刷新执行的操作
//                //请求数据
//                //清空原来的集合
//                mNewsList.clear();
//                data(mAdapter.danji, 1, 0);
            }
        });
        data(1, 1, 0);

        return mView;
    }

    private void init() {
        HttpClient.getIntance().get().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mS = response.body().string();
                try {
                    JSONObject mJSONObject = new JSONObject(mS);
                    if (mJSONObject.getInt("status") == 0) {
                        JSONArray mJSONArray = mJSONObject.getJSONArray("data");
                        for (int i = 0; i < mJSONArray.length(); i++) {
                            mJSONObject = mJSONArray.getJSONObject(i);
                            JSONArray mArray = mJSONObject.getJSONArray("subgrp");
                            for (int j = 0; j < mArray.length(); j++) {
                                JSONObject mObject = mArray.getJSONObject(j);
                                String mSubgroup = mObject.getString("subgroup");
                                int mSubid = mObject.getInt("subid");
                                NewsType mNewsType = new NewsType(mSubgroup, mSubid);
                                mNewsTypeList.add(mNewsType);
                            }
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                mAdapter.notifyDataSetChanged();

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void data(int subid, int dir, int nid) {
        String stamp="0";
            if(dir==1){
                nid=0;
                stamp="0";

            }else {
                nid=mNewsList.get(mNewsNews.getAdapter().getItemCount()-1).nid;
                stamp=mNewsList.get(mNewsNews.getAdapter().getItemCount()-1).stamp;
            }

        OkHttpClient mOkHttpClient = new OkHttpClient();
        String url = "news_list?ver=2&subid=" + subid + "&dir=" + dir + "&nid=" + nid + "&stamp=null&cnt=2";
        Request mRequest = new Request.Builder()
                .url(UrlUtils.APP_URL + url)
                .build();
        Call mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mNfSrl.setRefreshing(false);
                Log.e("onFailure", "shibai");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mS = response.body().string();
                Log.e("onResponse", mS);
                try {
                    JSONObject mJSONObject = new JSONObject(mS);
                    if (mJSONObject.getInt("status") == 0) {
                        JSONArray mJSONArray = mJSONObject.getJSONArray("data");
                        for (int i = 0; i < mJSONArray.length(); i++) {
                            mJSONObject = mJSONArray.getJSONObject(i);
                            String mSummary = mJSONObject.getString("summary");
                            String mIcon = mJSONObject.getString("icon");
                            String mStamp = mJSONObject.getString("stamp");
                            String mTitle = mJSONObject.getString("title");
                            int mNid = mJSONObject.getInt("nid");
                            String mLink = mJSONObject.getString("link");
                            int mType = mJSONObject.getInt("type");
                            News mNews = new News(mSummary, mIcon, mStamp, mTitle, mNid, mLink, mType);
                            mNewsList.add(mNews);

                        }
                        Log.e("TAG", mNewsList.toString());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mContentAdapter.notifyDataSetChanged();
//                                //停止下拉刷新
//                                mNfSrl.setRefreshing(false);
//                                mContentAdapter.notifyDataSetChanged();
//                                mNewsList2.clear();//清空第二个集合
//                                //给第二个集合付最新的值
//                                //给第二个集合赋值
//                                for (int i = 0; i < mNewsList.size(); i++) {
//                                    mNewsList2.add(mNewsList.get(i));
//                                }
                            }
                        });

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
