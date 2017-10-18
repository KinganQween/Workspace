package com.zhuoxin.easyshop.network;

import com.google.gson.Gson;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.model.GoodsUpLoad;
import com.zhuoxin.easyshop.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
/**
 * Created by Administrator on 2017/7/17 0017.
 * okhttp3网络框架构建
 */
public class EasyShopClient {
    private static EasyShopClient easyShopClient;
    private OkHttpClient okHttpClient;
    private Gson gson;
    //单例模式 懒汉式
    public static EasyShopClient getInstance(){
        if (easyShopClient==null){
            easyShopClient=new EasyShopClient();
        }
        return easyShopClient;
    }
    private EasyShopClient(){//构造方法私有化
        //日志拦截器，主要拦截响应行  响应头 响应体
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        //设置拦截器级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//获取响应行  响应头 响应体
        okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//添加日志拦截器
                .build();
        gson=new Gson();
    }

    /**
     * @param pageNo  分页加载 每一个页面显示20条商品
     * @param type 商品类型  “”为所有商品
     * @return
     */
    //获取所有商品
    public Call getGoods(int pageNo,String type){
        RequestBody requestBody=new FormBody.Builder()
                .add("pageNo",String.valueOf(pageNo))//pageNo=1
                .add("type",type)//type=""
                .build();
        Request request=new Request.Builder()
                //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=getAll&pageNo=1&type=""
                .url(EasyShopApi.BASE_URL+EasyShopApi.GETGOODS)//http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=getAll
                .method("POST",requestBody)
                .build();
        return okHttpClient.newCall(request);//返回一个Call模型
    }
    //获取商品详情
    //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=view&uuid=5606FF8EF60146A48F1FCDC34144907D
    public Call getGoodsData(String goodsUuid){
        RequestBody requestBody=new FormBody.Builder()
                .add("uuid",goodsUuid)//uuid=5606FF8EF60146A48F1FCDC34144907D
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.DETAIL)//http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=view
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
    //注册请求
    //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=register&username=...&password=...
    public Call register(String username,String password){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.REGISTER)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
    //登录请求
    //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=login&username=...&password=...
    public Call login(String username,String password){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.LOGIN)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
    //上传头像
    public Call uploadAvatar(File file){//上传图片文件
        RequestBody requestBody=new MultipartBody.Builder()
                //上传文件的方式
                .setType(MultipartBody.FORM)//multipart/form-data
                //需要一个用户类JSON字符串
                .addFormDataPart("user",gson.toJson(CachePreferences.getUser()))
                //"image"：key值   file.getName():文件名  RequestBody.create(MediaType.parse("image/png"),file)要上传的文件和文件类型
                .addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file))
                .build();
        //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update
       Request request=new Request.Builder()
               .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
               .post(requestBody)
               .build();
        return okHttpClient.newCall(request);
    }
    //修改昵称
    public Call uploadUser(User user) {
        //构建请求体
            RequestBody requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)//multipart/form-data
                    //传入一个用户的json字符串
                    .addFormDataPart("user",gson.toJson(user))
                    .build();
        //构建请求
            Request request=new Request.Builder()
                    //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update
                    .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
                    .post(requestBody)
                    .build();
        return okHttpClient.newCall(request);
    }
    //获取个人商品
    //商品每页20条数据
    //pageNo=1 //string
    //发布者
    //master="android"
    //类型
    //type="dress"    //空字符串可获取所有个人商品
    public Call getPersonData(int pageNo, String type, String master) {
        RequestBody requestBody=new FormBody.Builder()
                .add("pageNo",String.valueOf(pageNo))
                .add("master",master)
                .add("type",type)
                .build();
       Request request=new Request.Builder()
               //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=getAll
               .url(EasyShopApi.BASE_URL+EasyShopApi.GETGOODS)
               .post(requestBody)
               .build();
        return okHttpClient.newCall(request);
    }
    //删除商品
    /*
            路径： GoodsServlet?method=delete
            方法： post
            请求：
            //商品的uuid(商品表中的主键)
            uuid="....."
     */
    public Call deleteGoods(String uuid){
        RequestBody requestBody=new FormBody.Builder()
                .add("uuid",uuid)
                .build();
//http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=delete
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.DELETE)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
    //上传商品
    /*
    路径： GoodsServlet?method=add
    方法： post/Multipart
    请求：
    //请求是一个商品类的JSON字符串， 商品的图片以文件形式上传
    {
        "description": "诚信商家，非诚勿扰",     //商品描述
        "master": "android",                    //商品发布者
        "name": "礼物，鱼丸，鱼翅，火箭，飞机",   //商品名称
        "price": "88",                          //商品价格
        "type": "gift"                          //商品类型
    }
     */
    //files表示多个图片的文件
    public Call upLoad(GoodsUpLoad goodsUpLoad, ArrayList<File> files){
        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("good",gson.toJson(goodsUpLoad));
        //将所有图片添加进来
        for (File file:files){//将图片一张一张添加到请求体中
            builder.addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file));
        }
        RequestBody requestBody=builder.build();//构建请求体
        //构建请求
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPLOADGOODS)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
    //获取好友列表
    /*
    路径： UserWeb?method=getNames
    方法： post
    请求：
    //表单
    name=[环信id1,环信id2,....]
     */
    //ids 环信ID集合
    public Call getUsers(List<String> ids){
        String names=ids.toString();//"[1 2 3 4]"
        //去空格
        names=names.replace(" ","");//[1234]
        RequestBody requestBody=new FormBody.Builder()
                .add("name",names)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.GET_NAMES)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
    //根据用户昵称查询好友
    /*
    路径： UserWeb?method=getUsers
    方法： post
    请求：
    //表单
    nickname=用户昵称
     */
    public Call getSearchUser(String nickname){
        RequestBody requestBody=new FormBody.Builder()
                .add("nickname",nickname)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.GET_USER)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
}
