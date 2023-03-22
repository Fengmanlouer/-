package com.franz.easyweather.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class Helper extends SQLiteOpenHelper {
    /**数据库名称*/
    public static final String DataBase = "EasyWeather.db";
    public static final SQLiteDatabase.CursorFactory factory = null;
    public static int version = 1;
    /**表名*/
    public static final String TableName = "LocationTable";
    /**行名*/
    public static final String Row_Location = "Location";



    public Helper(@Nullable Context context) {
        super( context, DataBase, factory, version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TableName + "( "+Row_Location+" varchar(20) primary key);";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TableName);
        onCreate(db);
    }
}
