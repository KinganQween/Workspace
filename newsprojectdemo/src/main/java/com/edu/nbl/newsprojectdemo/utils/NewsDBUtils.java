package com.edu.nbl.newsprojectdemo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.nbl.newsprojectdemo.biz.NewsDBHelp;
import com.edu.nbl.newsprojectdemo.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-10-16.
 */

public class NewsDBUtils {



    public static  boolean insert(Context context,News news){
        //先查询有没有插入过
        NewsDBHelp mDBHelp=new NewsDBHelp(context);
        //getReadableDatabase()当磁盘空间充足时，以读写的方式打开数据库，当磁盘空间不足时，以只读的方式打开数据库
        //getWritableDatabase以读写的方式打开数据库，当磁盘空间不足时，会报错
        SQLiteDatabase db=mDBHelp.getReadableDatabase();
        //selectionArgs:查询参数(根据什么条件进行查询)
        // Cursor mCursor = db.rawQuery("select * from news", new String[]{news.nid + ""});
        Cursor mCursor=db.rawQuery("select * from news where nid=" + news.nid,null);
        if (mCursor.moveToNext()){
            //说明有数据 ，已经差入过
            return false;
        }
        //插入操作
        ContentValues mContentValues=new ContentValues();
        mContentValues.put("nid",news.nid);
        mContentValues.put("stamp",news.stamp);
        mContentValues.put("icon",news.icon);
        mContentValues.put("title",news.title);
        mContentValues.put("summary",news.summary);
        mContentValues.put("link",news.link);
        //返回值代表成功插入几行，如果插入失败就返回-1
        long mNews=db.insert("news",null,mContentValues);
        if (mNews!=-1){
            return true;

        }else {
            return false;
        }
    }
    public  static List<News> query(Context context){
        List <News> mNewsList=new ArrayList<>();
        NewsDBHelp mHelp=new NewsDBHelp(context);
        SQLiteDatabase db=mHelp.getReadableDatabase();
        Cursor mCursor=db.rawQuery("select * from news where length(title)<10 order By stamp desc", null);

        //遍历cursor
        while (mCursor.moveToNext()){
            int mNid = mCursor.getInt(mCursor.getColumnIndex("nid"));
            String mStamp = mCursor.getString(mCursor.getColumnIndex("stamp"));
            String mIcon = mCursor.getString(mCursor.getColumnIndex("icon"));
            String mTitle = mCursor.getString(mCursor.getColumnIndex("title"));
            String mSummary = mCursor.getString(mCursor.getColumnIndex("summary"));
            String mLink = mCursor.getString(mCursor.getColumnIndex("link"));
            News mNews = new News(mSummary, mIcon, mStamp, mTitle, mNid, mLink);
            mNewsList.add(mNews);
        }
        return mNewsList;
    }
    /**
     * 根据nid删除对应的条目的数据
     * @param context
     * @param news
     * @return
     */
    public static boolean deleteOne(Context context,News news){
        NewsDBHelp mHelper = new NewsDBHelp(context);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        int mDelete = db.delete("news", "nid=?", new String[]{news.nid + ""});//返回值代表成功删除了几行，-1代表删除失败
        if (mDelete!=-1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除所有的新闻数据
     * @param context
     * @return
     */
    public static boolean deleteAll(Context context){
        NewsDBHelp mHelper = new NewsDBHelp(context);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        int mDelete = db.delete("news", null, null);
        if (mDelete!=-1){
            return true;
        }else {
            return false;
        }

    }
}
