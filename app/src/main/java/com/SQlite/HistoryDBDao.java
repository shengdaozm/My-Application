package com.SQlite;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.publicClass.history;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

//历史数据表操作类
public class HistoryDBDao {
    public static final String TABLE_NAME = "history_info";//数据表名称
    public static String KEY_ID = "id";//数据库自增ID
    public static String KEY_URL = "url";//网页的网址
    public static String KEY_TEXT = "text";//网页的标题
    public static String KEY_WEBICON = "webIcon";//网页的图标


    private SQLiteDatabase mDatabase;//数据库
    private Context mContext;//    //上下文
    private SQLiteMaster.DBOpenHelper mDBOpenHelper;//数据库打开帮助类

    /**
     * 初始化该表的上下文
     * @param context 上下文
     */
    public HistoryDBDao(Context context){
        mContext = context;;
    }

    /**
     * 设置该数据表从属的数据库
     * @param db 数据库
     */
    public void setDatabase(SQLiteDatabase db){ mDatabase = db; }

    /**
     * 插入history对象
     * @param History history对象
     * @return 插入数据的条数
     */
    public long insertData(history History) {
        Long id = null;
        ContentValues values = new ContentValues();
        Log.d("TEST","3");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Log.d("TEST","4");
        //TODO 无法插入bitmap
        if(History.getWebIcon() != null) {
            History.getWebIcon().compress(Bitmap.CompressFormat.PNG, 100, os);
        }
        else os = null;
        Log.d("TEST","5");
        values.put(KEY_URL,History.getUrl());
        values.put(KEY_TEXT,History.getText());
        Log.d("TEST","6");
        if(os != null)
            values.put(KEY_WEBICON,os.toByteArray());
        else
            values.put(KEY_WEBICON,(Byte)null);
        Log.d("TEST","7");
        long num = mDatabase.insert(TABLE_NAME , null , values);
        Log.d("TEST","8");
        return num;
    }

    /**
     * 删除数据
     * @param id history的数据表ID
     * @return 输出数据的条数
     */
    public long deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    /**
     * 删除所有数据
     * @return 删除数据的条数
     */
    public long deleteAllData() {
        return mDatabase.delete(TABLE_NAME, null, null);
    }

    /**
     * 更新数据
     * @param id 要更新的数据的数据表ID
     * @param History 更新后的history对象
     * @return 更新数据的条数
     */
    public long updateData(int id , history History){
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        History.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,History.getUrl());
        values.put(KEY_TEXT,History.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        return mDatabase.update(TABLE_NAME,values,KEY_ID + "=" + id , null);
    }

    /**
     * 查询数据
     * @param id 查询数据的ID
     * @return history对象的链表
     */
    public List<history> queryData(int id){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON}, KEY_ID + "=" + id , null , null , null , null);
        return convertUtil(results);
    }

    /**
     * 查询所有数据
     * @return history对象的链表
     */
    public List<history> queryDataList(){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)) {return null;}
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON}, null , null , null , null , null);
        return convertUtil(results);
    }

    @SuppressLint("Range")
    /**
     * 对cursor查询结果进行转换
     * @param cursor 数据表的游标
     * @return history对象链表
     */
    private List<history> convertUtil(Cursor cursor){
        int resultCounts = cursor.getCount();
        if(resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        List<history> mList = new ArrayList<>();
        for(int i = 0 ; i < resultCounts ; i ++){
            history History = new history();
            History.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            History.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
            History.setText(cursor.getString(cursor.getColumnIndex(KEY_TEXT)));
            byte[] in = cursor.getBlob(cursor.getColumnIndex(KEY_WEBICON));
            Bitmap bmpout = BitmapFactory.decodeByteArray(in , 0 , in.length);
            History.setWebIcon(bmpout);
            mList.add(History);
            cursor.moveToNext();
        }
        return mList;
    }
}
