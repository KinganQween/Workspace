package com.edu.nbl.demo.mvptest.p;

import com.edu.nbl.demo.mvptest.m.IUserBiz;
import com.edu.nbl.demo.mvptest.m.UserBiz;
import com.edu.nbl.demo.mvptest.v.MNLoginActivity;

/**
 * Created by Administrator on 17-9-21.
 */

public class LoginPresent implements  Ipresenter{
    private UserBiz mUserBiz;
    private MNLoginActivity mActivity;
    public LoginPresent(MNLoginActivity mnLoginActivity){
        mUserBiz = new UserBiz();
        this.mActivity = mnLoginActivity;
    }
    @Override
    public void login() {
        mUserBiz.Login(mActivity.getUserName(), mActivity.getUserPsd(), new IUserBiz.OnLoginListener() {
            @Override
            public void onSuccess(String str) {
                //收到登录成功参数
                mActivity.onSuccessfulShow(str);
            }

            @Override
            public void onFailed(String error) {
                //收到登录失败的参数
                mActivity.failedShow(error);
            }
        });

    }
}
