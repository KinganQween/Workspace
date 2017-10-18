package com.edu.nbl.newsprojectdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.activity.NewsDetailsActivity;
import com.edu.nbl.newsprojectdemo.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-10-11.
 */

public class NewsFragmentContentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    List<News> mNewsList = new ArrayList<>();

    public List<News> getNewsList() {
        return mNewsList;
    }

    public void setNewsList(List<News> newsList) {
        mNewsList = newsList;
    }

    public NewsFragmentContentAdapter(Context context, List<News> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }

    private static final int TYPE_IV = 0;
    private static final int TYPE_TV = 1;

    public NewsFragmentContentAdapter(Context context) {
        mContext = context;
    }

    public int getItemViewType(int i) {
        if (i % 3 == 0) {
            return TYPE_IV;
        } else {
            return TYPE_TV;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IV) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
            return new ContentViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_iv, parent, false);
            return new ContentIVViewHolder(mView);
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final  News mNews=mNewsList.get(position);
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).mTextView.setText(mNewsList.get(position).title);
            Glide.with(mContext).load(mNewsList.get(position).icon).into(((ContentViewHolder) holder).mImageView1);
            Glide.with(mContext).load(mNewsList.get(position).icon).into(((ContentViewHolder) holder).mImageView2);
            Glide.with(mContext).load(mNewsList.get(position).icon).into(((ContentViewHolder) holder).mImageView3);

        } else if (holder instanceof ContentIVViewHolder) {
            Glide.with(mContext).load(mNewsList.get(position).icon).into(((ContentIVViewHolder) holder).mIIVVmageView);
            ((ContentIVViewHolder) holder).mIVVTextView1.setText(mNewsList.get(position).title);
            ((ContentIVViewHolder) holder).mIVVTextView2.setText(mNewsList.get(position).summary);
            ((ContentIVViewHolder) holder).mIVVTextView3.setText(mNewsList.get(position).stamp);
        }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent mIntent=new Intent(mContext, NewsDetailsActivity.class);
               Bundle mBundle=new Bundle();
               mBundle.putSerializable("News",mNews);
               mIntent.putExtras(mBundle);
               mContext.startActivity(mIntent);
           }
       });
    }


    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView1;
        private ImageView mImageView2;
        private ImageView mImageView3;

        public ContentViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.nf_item_ct_tv);
            mImageView1 = (ImageView) itemView.findViewById(R.id.nf_item_ct_iv1);
            mImageView2 = (ImageView) itemView.findViewById(R.id.nf_item_ct_iv2);
            mImageView3 = (ImageView) itemView.findViewById(R.id.nf_item_ct_iv3);

        }
    }

    static class ContentIVViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIIVVmageView;
        private TextView mIVVTextView1;
        private TextView mIVVTextView2;
        private TextView mIVVTextView3;

        public ContentIVViewHolder(View itemView) {
            super(itemView);
            mIIVVmageView = (ImageView) itemView.findViewById(R.id.nf_ct_iv_iv);

            mIVVTextView1 = (TextView) itemView.findViewById(R.id.nf_ct_iv_tvtitle);
            mIVVTextView2 = (TextView) itemView.findViewById(R.id.nf_ct_iv_summary);
            mIVVTextView3 = (TextView) itemView.findViewById(R.id.nf_ct_iv_stamp);
        }
    }
}
