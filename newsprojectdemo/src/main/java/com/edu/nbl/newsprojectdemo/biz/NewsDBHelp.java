package com.edu.nbl.newsprojectdemo.biz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 17-10-16.
 */

public class NewsDBHelp extends SQLiteOpenHelper {

    public NewsDBHelp(Context context) {
        super(context,"savannews.db",null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table news (id integer primary key autoincrement,nid integer,stamp text,icon text,title text,summary text,link text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
