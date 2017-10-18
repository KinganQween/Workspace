package com.zhuoxin.easyshop.main.me.personinfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/7/21 0021.
 * 个人信息的View层
 */

public interface PersonView extends MvpView{
    void showPrb();//显示progressbar
    void hidePrb();//隐藏progressbar
    void showMsg(String msg);//吐司
    //用来更新头像
    void updateAvatar(String url);
}
