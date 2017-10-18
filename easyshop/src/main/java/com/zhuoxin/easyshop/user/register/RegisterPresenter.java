package com.zhuoxin.easyshop.user.register;

import android.util.Log;

import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhuoxin.easyshop.commons.ConvertUser;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.model.User;
import com.zhuoxin.easyshop.model.UserResult;
import com.zhuoxin.easyshop.network.EasyShopClient;
import com.zhuoxin.easyshop.network.UICallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/20 0020.
 * 注册的业务处理层：执行网络操作，完成注册请求
 * 在特定的地方，使用RegisterView触发对应的UI操作
 */

public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>{
    private Call call;//call模型

    @Override
    public void attachView(RegisterView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){
            call.cancel();//释放内存
        }
        EventBus.getDefault().unregister(this);
    }
    //发送注册网络请求
    public void register(final String username, String password){
        getView().showPrb();//注册一开始显示progressbar
        call= EasyShopClient.getInstance().register(username,password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//主线程  失败的回调
                //隐藏progressbar
                getView().hidePrb();
                //显示异常信息
                getView().showMsg(e.getMessage());
            }/*
    {
        "code": 1,
        "msg": "succeed",
        "data": {
        "username": "xc01",
        "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
        "nickname": "666",
        "name": "yt0186129caad847a98a71189fda4f2300",
        "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
        " password": "123456"

        }
    }
             */
            @Override
            public void onResponseUI(Call call, String body) {//主线程 成功的回调
                //成功隐蔽progressbar
                getView().hidePrb();
                //拿到数据解析数据
                Log.d("Register","body="+body);
                UserResult result=new Gson().fromJson(body,UserResult.class);
                Log.d("Register","result="+result);
                if (result.getCode()==1){//根据不同的结果码处理
                    getView().showMsg("注册成功");//吐司注册成功
                    //注册成功保存用户信息
                    /*
                    "username": "xc62",
                    "name": "yt59856b15cf394e7b84a7d48447d16098",
                    "uuid": "0F8EC12223174657B2E842076D54C361",
                    "password": "123456"
                     */
                    User user=result.getData();
                    CachePreferences.setUser(user);//存储用户信息
                    //环信注册
                    EaseUser easeUser= ConvertUser.convert(user);
                    HxUserManager.getInstance().asyncLogin(easeUser,user.getPassword());
                    //注册成功 操作UI界面,注册成功进入市场页面

                }else if(result.getCode()==2){//注册失败
                    //执行注册失败
                    getView().registerFailed();
                }else{
                    getView().showMsg("未知错误");
                }
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        //判断是否注册成功
        if (event.type!= HxEventType.LOGIN) return;
        getView().hidePrb();
        getView().registerSuccess();
        getView().showMsg("注册成功");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
        if (event.type!=HxEventType.LOGIN)return;
        getView().hidePrb();
        getView().showMsg(event.toString());
    }

}
