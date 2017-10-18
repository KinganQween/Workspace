package com.zhuoxin.easyshop.main.me.persongoods;

import android.util.Log;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.easyshop.main.shop.ShopView;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.model.GoodsResult;
import com.zhuoxin.easyshop.network.EasyShopClient;
import com.zhuoxin.easyshop.network.UICallBack;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/24 0024.
 * 个人商品的presenter业务层
 */

public class PersonGoodsPresenter extends MvpNullObjectBasePresenter<ShopView>{
    private Call call;
    private int pageInt=1;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){
            call.cancel();
        }
    }
    //下拉刷新数据
    //类型共有household、electron、dress、toy、book、gift、other 分别对应家用、电子、服饰、玩具、图书、礼品、其它"
    public void refreshData(String type){//type是类型
        call= EasyShopClient.getInstance().getPersonData(1,type,CachePreferences.getUser().getName());
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showRefreshError(e.getMessage());
            }
            /*
            //成功
                {
                    "code": 1,
                    "msg": " success",
                    "datas": [
                            {
                                "price": "66",        //商品价格
                                "name": "单车",      //商品名称
                                "description": "......",  //商品描述
                                "page": "/images/D3228118230A43C0B77/5606FF8A48F1FC4907D/F99E38F09A.JPEG", //商品的第一张图片
                                "type": "other",      //商品类型
                                "uuid": "5606FF8EF60146A48F1FCDC34144907D",  //商品表中主键
                                "master": "android"  //发布者
                            }]
                }
             */
            @Override
            public void onResponseUI(Call call, String body) {
                Log.d("PERSONGOODS","body="+body);
                GoodsResult result=new Gson().fromJson(body,GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        if (result.getDatas().size()==0){//一个个人的商品也没有
                            getView().showRefreshEnd();//停止刷新
                        }else{//有个人商品
                            //addRefreshData需要在PersonGoodsActivity里重写，将商品集合添加到商品适配器集合中
                            getView().addRefreshData(result.getDatas());
                            getView().hideRefresh();//停止刷新
                        }
                        pageInt=2;
                        break;
                    default:
                        getView().showRefreshError(result.getMessage());//failed
                }
            }
        });
    }
    //上拉加载
    public void loadData(String type){
        getView().showLoadMoreLoading();
        call=EasyShopClient.getInstance().getPersonData(pageInt,type,CachePreferences.getUser().getName());
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showLoadMoreError(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.d("PERSONGOODS","loadbody="+body);
                GoodsResult result=new Gson().fromJson(body,GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        if (result.getDatas().size()==0){
                            getView().showLoadMoreEnd();
                        }else{
                            getView().addMoreData(result.getDatas());
                            getView().hideLoadMore();
                        }
                        pageInt++;
                        break;
                    default:
                        getView().showLoadMoreError(result.getMessage());

                }
            }
        });
    }
}
