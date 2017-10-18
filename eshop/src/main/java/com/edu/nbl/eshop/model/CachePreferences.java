package com.edu.nbl.eshop.model;

import android.content.Context;
import android.content.SharedPreferences;

public class CachePreferences {
	private static final String NANE=CachePreferences.class.getSimpleName();//获取CachePreferences类名
	private static final String KEY_USER_NAME="userName";//用户名
	private static final String KEY_USER_PWD="userPwd";//用户密码
	private static final String KEY_USER_HX_ID="userHxID";//环信id
	private static final String KEY_USER_TABLE_ID="userUuid";//用户识别码
	private static final String KEY_USER_HEAD_IMAGE="userHeadImage";//用户头像
	private static final String KEY_USER_NICKNAME="userNickName";//用户昵称
	private static SharedPreferences preferences;//存储用户状态的工具
	private static SharedPreferences.Editor editor;//存储用户状态的工具
	private CachePreferences(){
	}
	public static void init(Context context){
		preferences=context.getSharedPreferences(NANE,Context.MODE_PRIVATE);//创建CachePreferences.xml文件
		editor=preferences.edit();
	}
	/*
 "username": "xc01",
    "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
    "nickname": "666",
    "name": "yt0186129caad847a98a71189fda4f2300",
    "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
    "password": "123456"
}
 */
	//存储用户信息
	public static void setUser(User user){
		editor.putString(KEY_USER_HX_ID,user.getHx_Id());//环信id
		editor.putString(KEY_USER_NAME,user.getName());//用户名
		editor.putString(KEY_USER_PWD,user.getPassword());//用密码
		editor.putString(KEY_USER_TABLE_ID,user.getTable_Id());//用户标识码
		editor.putString(KEY_USER_HEAD_IMAGE,user.getHead_Image());//用户头像
		editor.putString(KEY_USER_NICKNAME,user.getNick_name());//用户昵称
		editor.commit();//提交数据
	}
	//获取用户信息
	public static User getUser(){//返回用户信息(返回自身信息)
		User user=new User();//构建一个用户存储用户信息
		user.setName(preferences.getString(KEY_USER_NAME,null));//获取用户名，存储到user中
		user.setNick_name(preferences.getString(KEY_USER_NICKNAME,null));//获取昵称，存储到user中
		user.setHx_Id(preferences.getString(KEY_USER_HX_ID,null));//获取环信id，存储到user中
		user.setHead_Image(preferences.getString(KEY_USER_HEAD_IMAGE,null));//获取头像地址，存储到user中
		user.setTable_Id(preferences.getString(KEY_USER_TABLE_ID,null));//获取用户标识码，存储到user中
		user.setPassword(preferences.getString(KEY_USER_PWD,null));//获取用户密码，存储到user中
		return user;//返回存储了信息的user
	}
	//清空用户信息
	public static void clearAllData() {
		editor.clear();
		editor.apply();//提交  效率高但是没有返回值
	}
}
