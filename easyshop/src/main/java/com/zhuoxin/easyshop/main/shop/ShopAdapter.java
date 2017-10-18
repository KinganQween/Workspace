package com.zhuoxin.easyshop.main.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.model.AvaterLoadOptions;
import com.zhuoxin.easyshop.model.GoodInfo;
import com.zhuoxin.easyshop.network.EasyShopApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 市场列表的适配器
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder>{
    private List<GoodInfo> list=new ArrayList<>();//存储所需的数据
    private Context context;//上下文
    //添加数据
    public void addData(List<GoodInfo> data){
        list.addAll(data);//添加上啦加载的数据
        notifyDataSetChanged();//适配器更新
    }
    //删除数据
    public void clear(){
        list.clear();//清空集合数据
        notifyDataSetChanged();//适配器更新
    }
    //创建ViewHolder
    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();//获取对应视图的Context实例
        //获取item布局
        View view= LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false);
        ShopViewHolder holder=new ShopViewHolder(view);//构建ViewHolder‘实例
        return holder;//返回给onBindViewHolder(ShopViewHolder holder, int position)
    }
    //通过holder绑定数据
    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        //获取每一个商品
        final GoodInfo goodInfo=list.get(position);
        //向对应的控件放置数据
        holder.tv_name.setText(goodInfo.getName());//商品名
        String price=context.getString(R.string.goods_money,goodInfo.getPrice());//把￥符号与价格进行拼接
        holder.tv_prive.setText(price);//商品价格
        //商品图片，UniversalImageLoader加载
        if (goodInfo.getPage()!=null){
            ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+goodInfo.getPage(),holder.imageView, AvaterLoadOptions.build_item());
        }else{
            holder.imageView.setBackgroundResource(R.drawable.image_error);
        }
        //在RecylerView的商品图片上设置点击效果，点完跳转到商品详情页
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/7/17 0017 接口回调
                if (listener!=null){
                    listener.onItemClickListener(goodInfo);//设置监听传递商品
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ShopViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_item_recycler)
        ImageView imageView;//商品图片
        @BindView(R.id.tv_item_name)
        TextView tv_name;//商品名称
        @BindView(R.id.tv_item_price)
        TextView tv_prive;//商品价格
        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);//从itemView上获取对应的控件
        }
    }
    //item图片的点击事件(接口回调)
    private OnItemClickListener listener;//声明监听器
    public interface OnItemClickListener{
        void onItemClickListener(GoodInfo goodInfo);//抽象方法
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){//设置item的监听器
        this.listener=onItemClickListener;
    }
}
