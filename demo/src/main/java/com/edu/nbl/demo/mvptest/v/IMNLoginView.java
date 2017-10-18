package com.edu.nbl.demo.mvptest.v;

/**
 * Created by Administrator on 17-9-21.
 */

public interface IMNLoginView {
    //登陆结果展示
    void  onSuccessfulShow(String string);
    void failedShow(String error);

    //提供用户名和密码
    String getUserName();
    String getUserPsd();
}
