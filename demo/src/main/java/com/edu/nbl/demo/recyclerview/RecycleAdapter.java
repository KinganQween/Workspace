package com.edu.nbl.demo.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.nbl.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 17-9-15.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

//    private Context mContext;
    private List<String> mList;
    String str1="aaaaa";
    String str2="bbbbbbbbb";
    public RecycleAdapter() {
//        this.mContext = context;
        mList=new ArrayList();
        mList.add("a");
    }
    public void add(List<String> list){
       this.mList.addAll(list);
    }
    public void add(String  str){
        mList.add(str);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string, parent,false);
        MyViewHolder mHolder = new MyViewHolder(mView);

        return mHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        switch (position%3){
            case 0:
                holder.mItemTv.setText(mList.get(position));
                break;
            case 1:
                holder.mItemTv.setText(mList.get(position)+str1);
                break;
            case 2:
                holder.mItemTv.setText(mList.get(position)+str2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv)
        TextView mItemTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemTv= (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
