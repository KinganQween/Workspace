package com.zhuoxin.easyshop.model;

import android.graphics.Bitmap;

import com.zhuoxin.easyshop.commons.Bimp;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/25 0025.
 * 图片上传时  RecyclerView里对应的item
 */

public class ImageItem {
    //图片名称
    private String imagePath;//File file=new File(imagePath)
    //.图片是否选中状态  默认为false没选中
    private boolean isCheck=false;
    //根据图片路径获得Bitmap
    private Bitmap bitmap;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
    //通过路劲获取Bitmap
    public Bitmap getBitmap() {
        if (bitmap==null){
            try {
                bitmap= Bimp.revisionImageSize(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "imagePath='" + imagePath + '\'' +
                ", isCheck=" + isCheck +
                ", bitmap=" + bitmap +
                '}';
    }
}
