package com.edu.nbl.demo.mvptest.m;

/**
 * Created by Administrator on 17-9-21.
 */

public interface IUserBiz {
    void Login(String name,String psd,OnLoginListener loginListener);//登录的业务

    void  register();//注册的业务
    void forgetpsd();//找回密码
     interface  OnLoginListener{
         void onSuccess(String str);
         void onFailed(String error);
     }
}
