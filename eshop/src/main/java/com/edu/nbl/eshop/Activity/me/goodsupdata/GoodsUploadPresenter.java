package com.edu.nbl.eshop.Activity.me.goodsupdata;

import android.util.Log;

import com.edu.nbl.eshop.commons.MyFileUtils;
import com.edu.nbl.eshop.model.GoodsUpLoad;
import com.edu.nbl.eshop.model.GoodsUpResult;
import com.edu.nbl.eshop.model.ImageItem;
import com.edu.nbl.eshop.network.EasyShopClient;
import com.edu.nbl.eshop.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 处理图片上传的业务层
 */

public class GoodsUploadPresenter extends MvpNullObjectBasePresenter<GoodsUploadView>{
    private Call call;
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){
            call.cancel();
        }
    }
    //商品上传的方法  之后再GoodsUploadActivity里调用
    public void upLoad(GoodsUpLoad goodsUpLoad, List<ImageItem> list){
        getView().showPrb();//显示progressbar
        call= EasyShopClient.getInstance().upLoad(goodsUpLoad,getFiles(list));
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//主线程 失败的回调
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }
//            成功
//            {
//                "code": 1,
//                    "msg": "添加成功！"
//            }
            @Override
            public void onResponseUI(Call call, String body) {//主线程 成功的回调
                Log.d("UPLOAD","body="+ body);
                GoodsUpResult result=new Gson().fromJson(body,GoodsUpResult.class);
                Log.d("UPLOAD","result="+result);
                if (result.getCode()==1){//上传成功
                    getView().upLoadSuccess();
                }else {
                    getView().showMsg(result.getMessage());//吐司错误信息
                }
            }
        });
    }
    //将ImageItem类型的集合变成File图片文件类型的集合
    private ArrayList<File> getFiles(List<ImageItem> list) {
        ArrayList<File> files=new ArrayList<>();
        for(ImageItem imageItem:list){
            //根据imageItem里的图片路径，拿到图片文件
            //mnt/shell/emulated/0/Photo_LJ/23451345431.jpeg
            File file=new File(MyFileUtils.SD_PATH+imageItem.getImagePath());
            files.add(file);
        }
        return files;
    }
}
