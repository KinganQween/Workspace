package com.zhuoxin.easyshop.main.shop;

import android.util.Log;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.easyshop.model.GoodInfo;
import com.zhuoxin.easyshop.model.GoodsResult;
import com.zhuoxin.easyshop.network.EasyShopClient;
import com.zhuoxin.easyshop.network.UICallBack;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 处理商品列表的业务
 */
public class ShopPresenter extends MvpNullObjectBasePresenter<ShopView>{
    private Call call;//Call模型 为了获取商品列表的
    private int pageInt=1;//下拉刷新是1   上拉加载>1
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){
            call.cancel();//销毁call模型
        }
    }
    //刷新数据 下拉刷新
    public void refreshData(String type){//商品类型  type=""所有商品
        getView().showRefresh();
        //刷新数据，永远是最新的数据，也就是说永远是第一页
        //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=getAll&pageNo=1&type=""
        call= EasyShopClient.getInstance().getGoods(1,type);//pageInt=1,type=""
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//前台线程 失败的响应
                getView().showRefreshError(e.getMessage());//吐出异常信息
            }

            @Override
            public void onResponseUI(Call call, String body) {//前台线程  成成功的响应
                Log.d("URL","body="+body);
                GoodsResult goodsResult=new Gson().fromJson(body,GoodsResult.class);
                Log.d("URL","goodsResult="+goodsResult);
                switch (goodsResult.getCode()){
                    case 1://成功
                        if (goodsResult.getDatas().size()==0){//一个商品也没有
                            getView().showRefreshEnd();//刷新结束
                        }else{//有商品
                            Log.d("URL","goodsResult.getDatas()="+goodsResult.getDatas());
                            getView().addRefreshData(goodsResult.getDatas());//将商品列表放入适配器集合中
                            getView().showRefreshEnd();//刷新结束
                        }
                        pageInt=2;//为了下一次的上拉加载
                        break;
                    default:
                        getView().showRefreshError(goodsResult.getMessage());//"msg": "failed"
                }
            }
        });
    }
    //上拉加载
    public void loadData(String type){
        getView().showLoadMoreLoading();//正在加载
        //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=getAll&pageNo=2&type=""
        call=EasyShopClient.getInstance().getGoods(pageInt,type);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//上拉加载失败回调
                getView().showLoadMoreError(e.getMessage());//吐异常错误信息
            }

            @Override
            public void onResponseUI(Call call, String body) {//上拉加载成功回调
                Log.d("URL","LoadMorebody="+body);
                GoodsResult goodsResult=new Gson().fromJson(body,GoodsResult.class);
                Log.d("URL","LoadMoregoodsResult="+goodsResult);
                switch (goodsResult.getCode()){
                    case 1://成功
                        if (goodsResult.getDatas().size()==0){//商品列表的长度为0
                            getView().showLoadMoreEnd();//结束上拉加载
                        }else{//有商品列表
                            getView().addMoreData(goodsResult.getDatas());//有商品放置到适配器集合，及时更新适配器
                            getView().showLoadMoreEnd();//加载结束
                        }
                        pageInt++;//分页加载，之后加载新一页的数据
                        break;
                    default://code==2失败
                        getView().showLoadMoreError(goodsResult.getMessage());// "failed"
                }
            }
        });
    }
}
