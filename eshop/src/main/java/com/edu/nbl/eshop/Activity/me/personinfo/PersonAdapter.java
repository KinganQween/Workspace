package com.edu.nbl.eshop.Activity.me.personinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.model.ItemShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 17-9-26.
 */

public class PersonAdapter extends BaseAdapter {
    private List<ItemShow> mList=new ArrayList<>();
    public PersonAdapter(List<ItemShow> mList) {//构造方法对成员变量初始化
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_info, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        //放数据
        mHolder.mTvItemName.setText(mList.get(position).getItem_title());//放置标题
        mHolder.mTvPerson.setText(mList.get(position).getItem_content());//放置内容
        return convertView;
    }
    static  class ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView mTvItemName;
        @BindView(R.id.tv_person)
        TextView mTvPerson;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
