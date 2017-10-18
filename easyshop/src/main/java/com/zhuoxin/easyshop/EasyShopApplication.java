package com.zhuoxin.easyshop;

import com.feicuiedu.apphx.HxBaseApplication;
import com.feicuiedu.apphx.HxModuleInitializer;
import com.feicuiedu.apphx.model.repository.DefaultLocalInviteRepo;
import com.feicuiedu.apphx.model.repository.DefaultLocalUsersRepo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhuoxin.easyshop.model.CachePreferences;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 全局使用的时候，就可以在Application里初始化
 */
public class EasyShopApplication extends HxBaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        CachePreferences.init(this);//初始化SharedPreferences和Editor实例
        //ImageLoader相关初始化
        DisplayImageOptions displayImageOptions=new DisplayImageOptions.Builder()
                .cacheOnDisk(true)//开启硬盘缓存
                .cacheInMemory(true)//开启内存缓存
                .resetViewBeforeLoading(true)//再加载前重置ImageView
                .build();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(1024*1024*4)//设置内存缓存的大小4M
                .defaultDisplayImageOptions(displayImageOptions)//设置选项配置
                .build();
        ImageLoader.getInstance().init(configuration);
    }
    //初始化环信模块
    @Override
    protected void initHxModule(HxModuleInitializer initializer) {
        initializer.setLocalInviteRepo(DefaultLocalInviteRepo.getInstance(this))
                .setLocalUsersRepo(DefaultLocalUsersRepo.getInstance(this))
                .setRemoteUsersRepo(new RemoteUserRep())//里面含有获取用户列表和查询好友的方法
                .init();
    }
}
