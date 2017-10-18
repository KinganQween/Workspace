package com.zhuoxin.easyshop.main.shop.details;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zhuoxin.easyshop.model.GoodsDetail;
import com.zhuoxin.easyshop.model.User;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public interface GoodsDetailView extends MvpView {
    void showProgress();//显示进度条
    void hideProgress();//隐藏进度条
    //设置图片路径
    void setImageData(ArrayList<String> arrayList);
    //设置商品信息
    void setData(GoodsDetail data, User goods_user);
    //商品不存在
    void showError();
    //提示信息
    void showMessage(String msg);
    //删除商品
    void deleteEnd();

}
