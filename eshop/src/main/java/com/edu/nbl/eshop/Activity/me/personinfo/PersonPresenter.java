package com.edu.nbl.eshop.Activity.me.personinfo;

import android.util.Log;

import com.edu.nbl.eshop.model.CachePreferences;
import com.edu.nbl.eshop.model.User;
import com.edu.nbl.eshop.model.UserResult;
import com.edu.nbl.eshop.network.EasyShopApi;
import com.edu.nbl.eshop.network.EasyShopClient;
import com.edu.nbl.eshop.network.UICallBack;
import com.feicuiedu.apphx.model.HxMessageManager;
import com.feicuiedu.apphx.model.HxUserManager;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/24 0024.
 *个人信息业务处理；类
 */

public class PersonPresenter extends MvpNullObjectBasePresenter<PersonView>{
    private Call call;//call模型

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){//销毁call模型
            call.cancel();
        }
    }
    //上传头像
    public void updateAvatar(File file){//file指的是头像图片
        getView().showPrb();//显示progressbar
        call= EasyShopClient.getInstance().uploadAvatar(file);//使用okhttp3上传头像
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//主线程失败的响应
                getView().hidePrb();//隐藏progressbar
                getView().showMsg(e.getMessage());//吐司错误信息
            }
            @Override
            public void onResponseUI(Call call, String body) {//主线程成功的响应
                Log.d("PHOTO","body="+body);
                getView().hidePrb();//成功隐藏progressbar
                UserResult result=new Gson().fromJson(body,UserResult.class);
                /*
                //成功
                {
                "code": 1,
                "msg": "success",
                "data": {}
                }
                 */
                if (result.getCode()==1){//上传成功
                    User user=result.getData();
                    CachePreferences.setUser(user);
                    //上传成功，触发UI操作(更新头像)
                    getView().updateAvatar(user.getHead_Image());
                    Log.d("TAG","上传成功");
                    //环信上传头像和设置头像
                    HxUserManager.getInstance().updateAvatar(EasyShopApi.IMAGE_URL+user.getHead_Image());//上传自己头像
                    HxMessageManager.getInstance().sendAvatarUpdateMessage(EasyShopApi.IMAGE_URL+user.getHead_Image());//设置自己头像
                }else if (result.getCode()==2){
                    getView().showMsg(result.getMessage());
                    Log.d("TAG","上传失败");
                }else{
                    getView().showMsg("未知错误");
                    Log.d("TAG","未知错误");
                }
            }
        });
    }


}
