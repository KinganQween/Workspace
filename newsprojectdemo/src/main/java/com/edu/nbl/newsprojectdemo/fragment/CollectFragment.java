package com.edu.nbl.newsprojectdemo.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.adapter.NewsFragmentContentAdapter;
import com.edu.nbl.newsprojectdemo.entity.News;
import com.edu.nbl.newsprojectdemo.entity.NewsEvent;
import com.edu.nbl.newsprojectdemo.utils.NewsDBUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 17-10-10.
 */

public class CollectFragment extends Fragment {
    @BindView(R.id.rc_collect)
    RecyclerView mRcCollect;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    Unbinder unbinder;
    List<News> mList;
    private NewsFragmentContentAdapter mContentAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_collect, container, false);
        unbinder = ButterKnife.bind(this, mView);
        mRcCollect.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList=new ArrayList<>();
        mList=NewsDBUtils.query(getActivity());
        mContentAdapter=new NewsFragmentContentAdapter(getActivity(),mList);
        mRcCollect.setAdapter(mContentAdapter);
        //刷新适配器
        mContentAdapter.notifyDataSetChanged();
        EventBus.getDefault().register(this);
        getData();
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onEvent(NewsEvent mEvent){
        //清空当前适配器中的集合
        //从数据库中获取新数据
        //刷新适配器
        mList.clear();
        mList=NewsDBUtils.query(getActivity());
        mContentAdapter=new NewsFragmentContentAdapter(getActivity(),mList);
        mRcCollect.setAdapter(mContentAdapter);
        mContentAdapter.notifyDataSetChanged();
    }


//    Handler mHandler=new Handler(){
//        private NewsFragmentContentAdapter mContentAdapter;
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            final List<News> mList= (List<News>) msg.obj;
//                //创建适配器
//                mContentAdapter=new NewsFragmentContentAdapter(getActivity(),mList);
//                mRcCollect.setAdapter(mContentAdapter);
//                //更新适配器
//                mContentAdapter.notifyDataSetChanged();
//        }
//    };

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<News> mList= NewsDBUtils.query(getActivity());
                Message mMessage=new Message();
                mMessage.obj=mList;
//                mHandler.sendMessage(mMessage);
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
