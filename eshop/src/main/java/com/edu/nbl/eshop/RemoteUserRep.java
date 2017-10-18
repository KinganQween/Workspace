package com.edu.nbl.eshop;

import com.edu.nbl.eshop.commons.ConvertUser;
import com.edu.nbl.eshop.model.GetUsersResult;
import com.edu.nbl.eshop.model.User;
import com.edu.nbl.eshop.network.EasyShopClient;
import com.feicuiedu.apphx.model.repository.IRemoteUsersRepo;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/27 0027.
 * 调用获取好友列表和查询好友
 */

public class RemoteUserRep implements IRemoteUsersRepo{
    //通过昵称查询好友
    @Override
    public List<EaseUser> queryByName(String username) throws Exception {
        Call call= EasyShopClient.getInstance().getSearchUser(username);//网络查询好友
        Response response= call.execute();////同步加载
        if (!response.isSuccessful()){
            throw new Exception(response.body().string());
        }
        //解析数据
        String json=response.body().string();//将响应变成字符串
        GetUsersResult result=new Gson().fromJson(json,GetUsersResult.class);
        //获取所有查询的好友
        List<User> users=result.getDatas();
        //把易淘用户变成环信用户
        List<EaseUser> easeUsers= ConvertUser.convertAll(users);
        return easeUsers;
    }
    //获取好友列表
    @Override
    public List<EaseUser> getUsers(List<String> ids) throws Exception {
        Call call=EasyShopClient.getInstance().getUsers(ids);
        Response response=call.execute();
        if (!response.isSuccessful()){
            throw new Exception(response.body().string());
        }
        String json=response.body().string();//将响应变成字符串
        //解析
        GetUsersResult result=new Gson().fromJson(json,GetUsersResult.class);
        if (result.getCode()==2){//响应失败
            throw  new Exception(result.getMessage());
        }
        //获取所有的用户，转换成环信的用户
        List<User> users=result.getDatas();
        List<EaseUser> easeUsers=ConvertUser.convertAll(users);
        return easeUsers;
    }
}
