package com.edu.nbl.eshop.User.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public interface RegisterView extends MvpView{
    //显示progressbar
    void showPrb();
    //隐藏progressbar
    void hidePrb();
    //注册成功
    void registerSuccess();
    //注册失败
    void registerFailed();
    //提示信息
    void showMsg(String msg);
}
