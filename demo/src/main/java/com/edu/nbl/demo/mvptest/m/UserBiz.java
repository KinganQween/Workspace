package com.edu.nbl.demo.mvptest.m;

/**
 * Created by Administrator on 17-9-21.
 */

public class UserBiz implements IUserBiz {
    private  boolean b;
    @Override
    public void Login(String name, String psd, OnLoginListener loginListener) {
        //登录的业务逻辑
        if (name.length()<3&&psd.length()<6){
            //首先检查密码或用户名是否符合要求
            //如果不符合要求，返回
            loginListener.onFailed("用户名或者密码不符合要求");
            return;
        }
        //模拟登录,一次成功一次失败
        if (b == true){
            loginListener.onSuccess("登录成功");
            b = false;
        }else {
            loginListener.onFailed("登录失败");
            b = true;
        }
    }

    @Override
    public void register() {

    }

    @Override
    public void forgetpsd() {

    }
}
