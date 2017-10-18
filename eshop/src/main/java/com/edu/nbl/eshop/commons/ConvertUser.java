package com.edu.nbl.eshop.commons;

import com.edu.nbl.eshop.model.User;
import com.edu.nbl.eshop.network.EasyShopApi;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/7/27 0027.
 * 将易淘User类转换成环信的EaseUser类
 */

public class ConvertUser {
    private ConvertUser(){
    }
    public static List<String> list;//声明集合
    public static List<String> getList(){
        return list;
    }
    //将易淘User集合变成环信EaseUser集合
    public static List<EaseUser> convertAll(List<User> users){
        if (users==null){
            return Collections.emptyList();//返回空集合
        }
        ArrayList<EaseUser> easeUsers=new ArrayList<>();
        for (User user:users){
            easeUsers.add(convert(user));
        }
        return easeUsers;//返回的是我们环信的用户集合
    }
    //将易淘的一个User转换成环信的一个EaseUser
    public static EaseUser convert(User user) {
        EaseUser easeUser=new EaseUser(user.getHx_Id());
        easeUser.setNick(user.getNick_name());//设置环信昵称
        easeUser.setAvatar(EasyShopApi.IMAGE_URL+user.getHead_Image());//更改环信头像
        EaseCommonUtils.setUserInitialLetter(easeUser);//将用户添加到环信中去
        return  easeUser;
    }
}
