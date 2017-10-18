package com.zhuoxin.easyshop.main.shop;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zhuoxin.easyshop.model.GoodInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 显示商品的UI接口
 */

public interface ShopView extends MvpView{
    //数据刷新
    void showRefresh();
    //数据刷新出错
    void showRefreshError(String msg);
    //数据刷新结束
    void showRefreshEnd();
    //数据刷新--隐藏下拉视图
    void hideRefresh();
    //数据刷新--添加新的数据
    void addRefreshData(List<GoodInfo> data);

    //加载更多--加载中
    void showLoadMoreLoading();
    //加载更多--加载错误
    void showLoadMoreError(String msg);
    //加载更多--没有更多数据
    void showLoadMoreEnd();
    //加载更多--隐藏加载更多视图
    void hideLoadMore();
    //加载更多--添加加载到的数据
    void addMoreData(List<GoodInfo> data);
    //显示消息
    void showMessage(String msg);

}
