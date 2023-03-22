package com.franz.easyweather.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;

public class Dao {
    public static final String TAG = "DataBase";
    private Helper helper ;
    private SQLiteDatabase DB;
    public Dao(Context context)
    {
        helper = new Helper(context);
    }
    public void Insert(String location){
        DB = helper.getReadableDatabase();
        if (DB.isOpen())
        {
            ContentValues values = new ContentValues();
            values.put(Helper.Row_Location,location);
            long RowId = DB.insert(Helper.TableName,null,values);
            if(RowId == -1)
                Log.i(TAG, "数据插入失败！");
            else
                Log.i(TAG, "数据插入成功！"+RowId);
        }
    }

    public void Delete(String location){
        DB = helper.getReadableDatabase();
        if (DB.isOpen()){
            String whereClause = "Location = ?";
            String[] whereArgs = {location + ""};
            int count = DB.delete(Helper.TableName, whereClause, whereArgs);
            if (count > 0)
                Log.i(TAG, "删除了: " + count + "行");
            else
                Log.i("todolist", "数据未删除！");
        }
    }

    public void DeleteAll(){
        DB = helper.getReadableDatabase();
        if (DB.isOpen()){
            int count = DB.delete(Helper.TableName, null, null);
            if (count > 0)
                Log.i(TAG, "删除了: " + count + "行");
            else
                Log.i("todolist", "数据未删除！");
        }
    }

    public boolean Query(String location) {
        DB = helper.getReadableDatabase();
        //selection查询子句的条件，可以使用主键查询
        String[] columns = {Helper.Row_Location};  // 需要的列
        String selection = "Location = ?";
        String[] selectionArgs = {location + ""};
        Cursor cursor = DB.query(Helper.TableName, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public List<String> QueryAll() {
        DB = helper.getReadableDatabase();  // 获得一个只读的数据库对象
        if(DB.isOpen()) {
            String[] columns = {Helper.Row_Location};  // 需要的列
            String selection = null;    // 选择条件, 给null查询所有
            String[] selectionArgs = null;  // 选择条件的参数, 会把选择条件中的? 替换成数据中的值
            String groupBy = null;  // 分组语句  group by name
            String having = null;   // 过滤语句
            String orderBy = null;  // 排序
            Cursor cursor = DB.query(Helper.TableName, columns, selection, selectionArgs, groupBy, having, orderBy);
            String location;
            if(cursor != null && cursor.getCount() > 0) {
                List<String> stringList = new ArrayList<>();
                while(cursor.moveToNext()) {    // 向下移一位, 知道最后一位, 不可以往下移动了, 停止.
                    location = cursor.getString(cursor.getColumnIndex(Helper.Row_Location ));
                    stringList.add(location);
                }
                return stringList;
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
