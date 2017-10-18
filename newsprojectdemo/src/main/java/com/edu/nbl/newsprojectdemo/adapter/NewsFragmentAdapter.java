package com.edu.nbl.newsprojectdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.entity.NewsType;

import java.util.List;

/**
 * Created by Administrator on 17-10-11.
 */

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.MyVieewHolder> {

    private List<NewsType> mNewsTypeList;
    private Context mContext;
    private    OnItemClickListener mOnItemClickListener;
    public   int danji ;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public NewsFragmentAdapter(Context context, List<NewsType> newsTypeList, OnItemClickListener mOnItemClickListener) {
        this.mNewsTypeList = newsTypeList;
        this.mContext = context;
        this.mOnItemClickListener=mOnItemClickListener;
    }

    public  interface  OnItemClickListener{
        void setOnItemClickListener(NewsType newsType,int position);
    }

    @Override
    public MyVieewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsfragment, null);
        return new MyVieewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MyVieewHolder holder, final int position) {
        holder.mTextView.setText(mNewsTypeList.get(position).subgroup);
        //接口回调
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.setOnItemClickListener(mNewsTypeList.get(position),position);
                }
            }
        });
        if (danji==position){
            holder.mTextView.setTextColor(Color.BLUE);
        }else {
            holder.mTextView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsTypeList.size();
    }

    static class MyVieewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public MyVieewHolder(View itemView) {

            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_news_fg);
        }
    }
}
