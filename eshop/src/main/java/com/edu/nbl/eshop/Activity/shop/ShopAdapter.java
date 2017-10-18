package com.edu.nbl.eshop.Activity.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.model.AvaterLoadOptions;
import com.edu.nbl.eshop.model.GoodInfo;
import com.edu.nbl.eshop.network.EasyShopApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 17-9-26.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {




    private List<GoodInfo> mList = new ArrayList<>();//存储所需的数据
    private Context mContext;//上下文

    //添加数据
    public void addData(List<GoodInfo> data) {
        mList.addAll(data);//添加上啦加载的数据
        notifyDataSetChanged();//适配器更新
    }

    //删除数据
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();//获取对应视图的Context实例
        //获取item布局
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        ShopViewHolder mHolder = new ShopViewHolder(mView);//构建ViewHolder‘实例
        return mHolder;//返回给onBindViewHolder(ShopViewHolder holder, int position)
    }

    //通过holder绑定数据
    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        //获取每一个商品
        final GoodInfo mGoodInfo = mList.get(position);
        //相对应的控件放置数据
        holder.mTv_Name.setText(mGoodInfo.getName());
        String price = mContext.getString(R.string.goods_money, mGoodInfo.getPrice());//把￥符号与价格进行拼接
        holder.mTv_Price.setText(price);//商品价格
        //商品图片，UniversalImageLoader加载
        if (mGoodInfo.getPage() != null) {
            ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL + mGoodInfo.getPage(), holder.mImageView, AvaterLoadOptions.build_item());
        } else {
            holder.mImageView.setBackgroundResource(R.drawable.image_error);
        }
        //在RecylerView的商品图片上设置点击效果，点完跳转到商品详情页
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClickListener(mGoodInfo);//设置监听传递商品
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ShopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView mTv_Name;
        @BindView(R.id.tv_item_price)
        TextView mTv_Price;
        @BindView(R.id.iv_item_recycler)
        ImageView mImageView;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);//从itemView上获取对应的控件

        }

    }
    //item图片的点击事件（接口回调）
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClickListener(GoodInfo goodInfo);//抽象方法
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){//设置item的监听器
        this.listener=onItemClickListener;
    }
}
