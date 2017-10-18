package com.edu.nbl.newsprojectdemo.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 17-8-31.
 */

public class LoadMoreForRecyclerView {
    private int state;//记录recyclerview的状态
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItemPostion;
    private int offsetY;
    private float moveY;
    private float oldY;
    private Context mContext;
    public LoadMoreForRecyclerView(RecyclerView recyclerView, LoadMoreListener loadMoreListener, Context context){
        this.mContext=context;
        width(recyclerView,loadMoreListener);
    }
    private void width(final RecyclerView recyclerView,LoadMoreListener loadMreListener){
     mLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
        mLoadMoreListener=loadMreListener;

        //recyclerview滚动状态监听
       recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
               state=newState;
               //最后一条可见条目
               mLastVisibleItemPostion=mLayoutManager.findLastVisibleItemPosition();
           }
            //滚动时调用
           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               offsetY=dy;
               Log.e("TAG",offsetY+"");
           }
       });
        //设置rectclerview的触摸时间的监听
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE://手指触摸
                        moveY=event.getY()-oldY;
                        oldY=event.getY();
                        Log.e("TAG",moveY+"");
                        break;
                    case MotionEvent.ACTION_UP://手指抬起
                        //SCROLL_STATE_SETTLING快速滑动
                        //SCROLL_STATE_DRAGGING触摸滑动
                        //如果最后一个可见条目是适配器中的最后一ge数据
                        if (state==RecyclerView.SCROLL_STATE_SETTLING||state==RecyclerView.SCROLL_STATE_DRAGGING&&recyclerView.getAdapter().getItemCount()-1==mLastVisibleItemPostion){
                            if (mLoadMoreListener!=null){
                                if (offsetY>0){
                                    mLoadMoreListener.loadMore();//加载更多
                                    Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                                }
                                if (offsetY==0){
                                    if (moveY<0){
                                        if (mLoadMoreListener!=null){
                                            mLoadMoreListener.loadMore();
                                            Toast.makeText(mContext,"没有新数据",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                }
                return false;
            }
        });
    }
public LoadMoreListener mLoadMoreListener;
    public interface LoadMoreListener{
        void loadMore();//加载更多
    }
}
