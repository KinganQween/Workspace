package com.edu.nbl.newsprojectdemo.biz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by z-015 on 2017/8/10.
 * <p>
 * 图片的三级缓存
 * <p>
 * 1.存
 * 1.1首相从网络下载得到图片
 * 1.2. ---  1.3  存到内从当中和本地中
 * <p>
 * <p>
 * 2.取
 * 2.1从内存读取
 * 2.2从本地读取
 * 2.3从网络下载
 * <p>
 * 图片的三级缓存
 * <p>
 * 1.存
 * 1.1首相从网络下载得到图片
 * 1.2. ---  1.3  存到内从当中和本地中
 * <p>
 * <p>
 * 2.取
 * 2.1从内存读取
 * 2.2从本地读取
 * 2.3从网络下载
 */

/**
 * 图片的三级缓存
 *
 * 1.存
 *    1.1首相从网络下载得到图片
 *     1.2. ---  1.3  存到内从当中和本地中
 *
 *
 * 2.取
 *      2.1从内存读取
 *      2.2从本地读取
 *      2.3从网络下载
 *
 */

/**
 * 3.异步任务：
 *      构造方法需要传进来一些参数，参数根据下面使用到时决定
 *      网络请求下载图片
 *      将图片转换成bitmap
 *      转到--->1.存
 *      将bitmap存到内存和本地---->这个过程中，需要截取url，获得图片名字
 *      完成后，将bitmap设置到imageview中，即在onPostExecute方法中完成收尾工作
 */

public class LoadImage {
    //获得手机运行内存
   private  static long maxMemory = Runtime.getRuntime().maxMemory() ;
    //最近最少算法---开辟一个规定大小的内存空间
    private static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>((int) maxMemory) {
        @Override//计算图片的的大小
        protected int sizeOf(String key, Bitmap value) {
            int a = value.getByteCount();
            return a;
        }
    };

    //构造方法
    public static void LoadImage(Context context, String url, ImageView iv) {
        //http://118.244.212.82:9092/Images/21060517114849.jpg
        int a = url.lastIndexOf("/");//获得最后一个“/”的下标
        //截取最后的一段，作为图片存储的名字
        String fileName = url.substring(a + 1);

        //从内存取
        Bitmap bitmap = lruCache.get(fileName);
        if (bitmap != null) {
            Log.i("123456", "从内存中获得");
            iv.setImageBitmap(bitmap);//bitmap不为空，则完成取任务，直接设置
            return;//图片已经获得，然后此次操作结束，直接返回
        }
        //从本地取

        File cacheDir = context.getCacheDir();
        //首先判断这儿目录是否存在
        if (cacheDir.exists()) {
            //获得此目录下的所有目录
            File[] files = cacheDir.listFiles();
            //使用for循环，便利这个数组，获得这个数组中文件的名字，进行比较
            for (File file : files) {
                //如果文件的名字和url截取的相同
                if (fileName.equals(file.getName())) {
                    Log.i("123456", "从本地中获得");
                    // 获得这张图片
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    iv.setImageBitmap(bitmap);
                    return;//图片已经获得，然后此次操作结束，直接返回
                }
            }
        } else {
            //如果不存在，先创建这个目录
            cacheDir.mkdir();
        }

        //从网络下载图片
        MyAsyncTask task = new MyAsyncTask(context, fileName, iv);
        task.execute(url);
    }

    /***********************异步任务  AsyncTask********************************/
   private static class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private Context context;//上下文，获得缓存路径
        private ImageView iv;//和图片绑定的控件
        private String fileName;

        //构造方法
        public MyAsyncTask(Context context, String fileName, ImageView iv) {
            this.context = context;
            this.iv = iv;
            this.fileName = fileName;
        }

        //耗时任务，进行网络下载，----需要一个url
        @Override
        protected Bitmap doInBackground(String... strings) {
            Log.i("123456", "执行网络下载任务");
            //1.获得执行异步任务时传进来的url
            String url = strings[0];

            //2.网络开始下载
            InputStream is = httpRequest(url);
            //3.判断网络请求是否成功
            if (is == null) {
                return null;
            }
            //4.转换，将流转换成图片
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            /*****    将图片保存到内存和本地    *******/
            if (bitmap == null) {
                return null;
            }
            //5.存到内存
            lruCache.put(fileName, bitmap);

            /**
             * 6.存到本地
             *  6.1 需要获得路径
             *  6.2 保存到这个路径
             */
            try {
                //6.1 获得缓存路径
                File caCheDir = context.getCacheDir();
                //文件输出流
                FileOutputStream outFile = new FileOutputStream(new File(caCheDir, fileName));
                //6.2压缩保存 --- 三个参数，1.图片格式  2.图片质量,100是完整的   3.文件输出流
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        //下载成功后，将图片设置到ImageView中
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                Log.i("123456", "下载设置图片失败");
            } else {
                //绑定图片
                iv.setImageBitmap(bitmap);
                Log.i("123456", "下载设置图片yes");
            }
        }


        //网络下载图片
        private InputStream httpRequest(String url) {
            //声明一个客户端,8秒连接/读写超时
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS).build();

            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Log.i("123456", "下载图片成功");
                    //连接成功，返回图片的流
                    return response.body().byteStream();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("123456", "网络连接失败");
            //失败返回空
            return null;
        }

    }


}
