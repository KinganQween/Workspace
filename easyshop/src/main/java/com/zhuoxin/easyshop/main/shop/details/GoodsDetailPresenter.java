package com.zhuoxin.easyshop.main.shop.details;

import android.util.Log;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.easyshop.model.GoodsDetail;
import com.zhuoxin.easyshop.model.GoodsDetailResult;
import com.zhuoxin.easyshop.network.EasyShopClient;
import com.zhuoxin.easyshop.network.UICallBack;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/19 0019.
 * 商品详情presnter层
 */

public class GoodsDetailPresenter extends MvpNullObjectBasePresenter<GoodsDetailView>{
    //获取商品详情的call
    private Call getDetailCall;
    private Call deleteCall;

    //将view层和presenter层解除依附
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (getDetailCall!=null){
            getDetailCall.cancel();
        }
        if (deleteCall!=null){
            deleteCall.cancel();
        }
    }
    //获取商品详情数据
    public void getData(String uuid){
        getView().showProgress();//显示旋转的进度条
        getDetailCall= EasyShopClient.getInstance().getGoodsData(uuid);//构建okhttp3网络请求
        getDetailCall.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//主线程失败 的回调
                getView().hideProgress();//隐藏旋转的progressbar
                getView().showMessage(e.getMessage());//土司失败信息
            }
            @Override
            public void onResponseUI(Call call, String body) {//主线程成功回调
                getView().hideProgress();//隐藏旋转的progressbar
                /*
                    GoodsDetailResult
                    "code": 1,
                    "msg": " success",
                    "datas": []
                    "user":{}
                 */
                GoodsDetailResult result=new Gson().fromJson(body,GoodsDetailResult.class);
                Log.d("TAG","GoodsDetailResult="+result);
                if (result.getCode()==1){//成功获取商品详情
                    /*
                    "uuid": "5606FF8EF60146A48F1FCDC34144907D",     //商品表中主键
                    "name": "货物",                                   //商品名称
                    "type": "other",                                //商品类型
                    "price": "66",                                  //商品价格
                    "description": ".......",                       //商品描述
                    "master": "android",                            //商品发布者
                    "pages":[]                                      //商品图片
                     */
                    //真正的商品详情
                    GoodsDetail goodsDetail=result.getDatas();
                    //集合用于存放商品图片的路径
                    ArrayList<String> list=new ArrayList<String>();
                    for(int i=0;i<goodsDetail.getPages().size();i++){//遍历图片地址集合
                        //  /images/D3228118230A430B77/5606FF8F1FCDC34144907D/F99E38F09A.JPEG
                        String pageUri=goodsDetail.getPages().get(i).getUri();//获取每一个图片地址
                        list.add(pageUri);
                    }
                    getView().setImageData(list);//让GoodsDetailActivity去实现此方法
                    getView().setData(goodsDetail,result.getUser());//主要是获取用户的昵称

                }else{//code不是1
                    getView().showError();
                }

            }
        });

    }
    //删除商品
    public void delete(String uuid){
        getView().showProgress();
        deleteCall=EasyShopClient.getInstance().deleteGoods(uuid);//发送网络请求
        deleteCall.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//主线程 失败
                getView().hideProgress();//隐藏progressbar
                getView().showMessage(e.getMessage());//吐司错误信息
            }
            @Override
            public void onResponseUI(Call call, String body) {//主线程 成功
                Log.d("DELETE","body="+body);
                getView().hideProgress();//隐藏progressbar
                GoodsDetailResult result=new Gson().fromJson(body,GoodsDetailResult.class);
                /*
                //成功
                    {
                        "code": 1,
                        "msg": "success"
                    }
                 */
                if (result.getCode()==1){//删除成功
                    //执行删除商品的方法
                    getView().deleteEnd();
                    getView().showMessage("删除成功");
                }else{
                    getView().showMessage("删除失败");
                }
            }
        });
    }
}
