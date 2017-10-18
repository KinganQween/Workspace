package com.zhuoxin.easyshop.model;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zhuoxin.easyshop.R;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 用于图片加载使用
 */

public class AvaterLoadOptions {
    private AvaterLoadOptions(){//1.构造方法私有化
    }
    private static DisplayImageOptions option_item;//加载图片过程中的属性设置
    public static DisplayImageOptions build_item(){
        if (option_item==null){//如果此实力为空值
            option_item=new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.image_error)//如果uri为空显示什么
                    .showImageOnFail(R.drawable.image_error)//图片加载失败显示什么
                    .showImageOnLoading(R.drawable.image_error)//图片加载过程中显示什么
                    .cacheOnDisk(true)//硬盘缓存
                    .cacheInMemory(true)//内存缓存
                    .resetViewBeforeLoading(true)//加载前重置
                    .build();
        }
        return option_item;
    }
    //加载头像图片的属性设置
    private static DisplayImageOptions options;
    public static DisplayImageOptions build(){
        if (options==null){
            options=new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.user_ico)//如果uri为空显示什么
                    .showImageOnFail(R.drawable.user_ico)//图片加载失败显示什么
                    .showImageOnLoading(R.drawable.user_ico)//图片加载过程中显示什么
                    .cacheInMemory(true)//内存缓存
                    .cacheOnDisk(true)//硬盘缓存
                    .resetViewBeforeLoading(true)//加载前重置
                    .build();
        }
        return options;
    }
}
