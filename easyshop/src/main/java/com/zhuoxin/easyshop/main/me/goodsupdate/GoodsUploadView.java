package com.zhuoxin.easyshop.main.me.goodsupdate;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public interface GoodsUploadView extends MvpView{
    void showPrb();
    void hidePrb();
    void upLoadSuccess();//上传成功
    void showMsg(String msg);
}
