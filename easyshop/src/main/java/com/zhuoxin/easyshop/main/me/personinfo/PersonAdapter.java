package com.zhuoxin.easyshop.main.me.personinfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.model.ItemShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21 0021.
 * 个人信息类ListView里的适配器
 */

public class PersonAdapter extends BaseAdapter {
    private List<ItemShow> list = new ArrayList<>();

    public PersonAdapter(List<ItemShow> list) {//构造方法对成员变量初始化
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_info, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //放数据
        holder.tvItemName.setText(list.get(position).getItem_title());//放置标题
        holder.tvPerson.setText(list.get(position).getItem_content());//放置内容
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView tvItemName;//title
        @BindView(R.id.tv_person)
        TextView tvPerson;//内容

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
