package com.zhuoxin.easyshop.user.login;

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
 * 执行登录网络请求的业务层
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{
    private Call call;//获取一个call模型
    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){
            call.cancel();//销毁call模型
        }
        EventBus.getDefault().unregister(this);
    }
    //执行登录请求
    public void login(String username,String password){
        getView().showPrb();//显示progressbar
        call= EasyShopClient.getInstance().login(username,password);//发送登录请求，传用户名和密码参数
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//主线程失败的操作
                getView().hidePrb();//隐藏progressbar
                getView().showMsg(e.getMessage());//吐司错误信息
            }
            /*
         {
            "code": 1,
            "msg": "succeed",
            "data": {
            "username": "xc01",
            "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
            "nickname": "666",
            "name": "yt0186129caad847a98a71189fda4f2300",
            "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
            "password": "123456"
             }
         }
             */
            @Override
            public void onResponseUI(Call call, String body) {//主线程成功的操作
                getView().hidePrb();//隐藏progressbar
                Log.d("Login","body="+body);
                UserResult result=new Gson().fromJson(body,UserResult.class);
                Log.d("Login","result="+result);
                if (result.getCode()==1){//登录成功
                    getView().showMsg("登陆成功");//吐司登录成功
                    /*
                    "username": "xc01",
                    "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
                    "nickname": "666",
                    "name": "yt0186129caad847a98a71189fda4f2300",
                    "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
                    "password": "123456"
                     */
                    User user=result.getData();//获取用户信息
                    CachePreferences.setUser(user);//存储用户名信息
                    //环信登录
                    EaseUser easeUser= ConvertUser.convert(user);
                    HxUserManager.getInstance().asyncLogin(easeUser,user.getPassword());
                }else if (result.getCode()==2){//登录失败
                    getView().hidePrb();//隐藏progressbar
                    getView().loginFailed();//执行登录失败UI
                }else{
                    getView().hidePrb();//隐藏progressbaret
                    getView().showMsg("未知错误");
                }
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        if (event.type!= HxEventType.LOGIN)return;
        //表示登录成功
        getView().showMsg("登录成功");
        getView().loginSuccess();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
        if (event.type!=HxEventType.LOGIN)return;
        getView().showMsg("环信地址不对");
    }
}
