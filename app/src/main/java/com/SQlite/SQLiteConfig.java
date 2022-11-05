package com.SQlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据表管理类
 */
public class SQLiteConfig {

    public static final String DB_NAME = "mSQLite.db";//数据库名称
    public static final int DB_VERSION = 1;//数据库版本

    /**
     * 判断数据库中的数据表是否为空
     * @param db 数据库
     * @param tablename 数据表表名
     * @return 如果为空则返回false
     */
    public static boolean HaveData(SQLiteDatabase db , String tablename){
        Cursor cursor;
        boolean a = false;
        cursor = db.rawQuery("select name from sqlite_master where type = 'table' " , null);
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
          if(name.equals(tablename)){
                a = true;
            }
        }
        if(a){
            cursor = db.query(tablename , null , null, null , null , null , null);
            //检查是不是空表
            return cursor.getCount() > 0;
        } else{
            return false;
        }
    }
}
