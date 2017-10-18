package com.edu.nbl.eshop.User.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/7/20 0020.
 * 登录的view层
 */

public interface LoginView extends MvpView{
    void showPrb();//显示progressbar
    void hidePrb();//隐藏progressbar
    void loginSuccess();//登录成功
    void loginFailed();//登录失败
    void showMsg(String msg);//执行吐司操作
}
