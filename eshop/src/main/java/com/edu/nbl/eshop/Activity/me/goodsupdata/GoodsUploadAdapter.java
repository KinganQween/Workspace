package com.edu.nbl.eshop.Activity.me.goodsupdata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.model.ImageItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25 0025.
 * 商品上传的RecyclerView的适配器
 */

public class GoodsUploadAdapter extends RecyclerView.Adapter{
    //适配器的数据  商品图片的集合  不包含加号图片
    private ArrayList<ImageItem> list=new ArrayList<>();
    private LayoutInflater inflater;//布局填充器
    public GoodsUploadAdapter(Context context,ArrayList<ImageItem> list) {
        this.inflater =LayoutInflater.from(context);
        this.list=list;
    }
    //枚举,item类型，ITEM_NORMAL==0-->有图   ITEM_ADD==1-->加号
    public enum ITEM_TYPE{
        ITEM_NORMAL,ITEM_ADD;
    }
    //商品图片的编辑模式  1-->不可编辑   2-->可编辑(有CheckBox的)
    public static final int MODE_NORMAL=1;
    public static final int MODE_MULTI_SELECT=2;
    //mode代表图片的编辑模式
    public int mode;
    //设置模式
    public void changeMode(int mode){
        this.mode=mode;
        // TODO: 2017/7/25 0025 更新适配器
        notifyDataSetChanged();//更新适配器
    }
    //获取当前模式
    public int getMode(){
        return mode;
    }
    //添加图片
    public void add(ImageItem imageItem){
        list.add(imageItem);
    }
    //获取商品集合的大小
    public int getSize(){
        return list.size();
    }
    //获取所有上传商品数据
    public ArrayList<ImageItem> getList(){
        return list;
    }
    public void notifyData(){
        // TODO: 2017/7/25 0025 更新适配器
        notifyDataSetChanged();//更新适配器
    }
    //根据类型获取不同的ViewHolder
    //只要position==商品的个数  就显示加号图片
    @Override
    public int getItemViewType(int position) {
        if (position==list.size()){//就显示加号图片
            return ITEM_TYPE.ITEM_ADD.ordinal();//就是1，1显示加号图片
        }
        //
        // 返回给了下面的viewType
        return ITEM_TYPE.ITEM_NORMAL.ordinal();//0,显示商品
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断当前显示item的类型，0,显示商品的布局   1显示加号图片布局
        if (viewType==ITEM_TYPE.ITEM_ADD.ordinal()){//1显示加号图片布局
            return new ItemAddViewHolder(inflater.inflate(R.layout.layout_item_recyclerviewlast,parent,false));//返回加号的ViewHolder
        }else{//0显示商品图片布局
            return new ItemSelectViewHolder(inflater.inflate(R.layout.layout_item_recyclerview,parent,false));//返回商品的ViewHolder
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemAddViewHolder){//表示加号的ViewHolder
            ItemAddViewHolder itemAddViewHolder= (ItemAddViewHolder) holder;//显示加号的图片
            if (position==8){
                itemAddViewHolder.ib_add.setVisibility(View.GONE);//让第9个加号图片不显示
            }else{
                itemAddViewHolder.ib_add.setVisibility(View.VISIBLE);//如果不是第9个就显示
                itemAddViewHolder.ib_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener!=null){
                            listener.onAddClicked();//点击加号图片回调   弹出popupWindow
                        }
                    }
                });
            }

        }else if (holder instanceof ItemSelectViewHolder){//表示商品的ViewHolder
            //获取数据
            final ImageItem imageItem=list.get(position);
            //获取ViewHolder
            final ItemSelectViewHolder itemSelectViewHolder= (ItemSelectViewHolder) holder;
            //存储item数据
            itemSelectViewHolder.photo=imageItem;
            //判断模式(正常,可多选删除)
            if (mode==MODE_MULTI_SELECT){//可多选  即有CheckBox
                itemSelectViewHolder.checkBox.setVisibility(View.VISIBLE);//显示CheckBox
                //给CheckBox设置监听器
                itemSelectViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        list.get(position).setCheck(isChecked);//可选框的改变 (改变实体类的选择状态)
                    }
                });
                itemSelectViewHolder.checkBox.setChecked(itemSelectViewHolder.photo.isCheck());//表示显示CheckBox和实体类的CheckBox同步
            }else if (mode==MODE_NORMAL){//正常显示商品图片
                itemSelectViewHolder.checkBox.setVisibility(View.GONE);//去掉CheckBox
            }
            //图片设置
            itemSelectViewHolder.iv_photo.setImageBitmap(imageItem.getBitmap());
            //商品图片的监听器
            itemSelectViewHolder.iv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/7/25 0025 使用一个回调接口 跳转到商品图片的展示页
                    if (listener!=null){
                        listener.onPhotoClicked(itemSelectViewHolder.photo);//传的是imageItem
                    }
                }
            });
            //商品图片的长按监听  长按模式变为MODE_MULTI_SELECT  即有checkbox
            itemSelectViewHolder.iv_photo.setOnLongClickListener(new View.OnLongClickListener() {//长按监听
                @Override
                public boolean onLongClick(View v) {
                    mode=MODE_MULTI_SELECT;//变成可多选的模式
                    notifyDataSetChanged();//刷新适配器
                    if (listener!=null){
                        listener.onLongClicked();//回调长按的监听器
                    }
                    Log.d("CLICK","LONGCLICK");
                    return false;
                }
            });


        }

    }
    @Override
    public int getItemCount() {
        return Math.min(list.size()+1,8);//最多只显示8个item布局
    }
    //写两个ViewHolder加载不同item布局
    //加号图片的ViewHolder
    public static class ItemAddViewHolder extends RecyclerView.ViewHolder{
        //对控件的绑定(优化)
        @BindView(R.id.ib_recycle_add)
        ImageButton ib_add;
        public ItemAddViewHolder(View itemView) {//itemView这是加号的item布局
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //显示商品图片的ViewHolder
    public static class ItemSelectViewHolder extends RecyclerView.ViewHolder{
        //绑定控件
        @BindView(R.id.iv_photo)
        ImageView iv_photo;
        @BindView(R.id.cb_check_photo)
        CheckBox checkBox;
        ImageItem photo;//用来控制checkBox的选择属性
        public ItemSelectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //item点击事件 (接口回调)
    private OnItemClickListener listener;
    public interface  OnItemClickListener{
        //点击加号图片回调
        void onAddClicked();
        //点击商品图片回调
        void onPhotoClicked(ImageItem imageItem);
        //商品图片长按回调
        void onLongClicked();
    }
    //设置监听器
    public void setListener(OnItemClickListener listener){
        this.listener=listener;
    }

}
