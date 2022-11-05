package com.SQlite;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.publicClass.collection;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

//收藏数据表操作类
public class CollectionDBDao {
    //数据库名称
    public static final String TABLE_NAME = "collection_info";

    public static String KEY_ID = "id";//数据库自增ID
    //表的字段名
    public static String KEY_URL = "url";//网址的字段名
    public static String KEY_TEXT = "text";//网页内容标题的字段名
    public static String KEY_WEBICON = "webIcon";//网页的图标的字段名
    public static String KEY_LABEL = "label";//用户自定义标签的字段名

    private SQLiteDatabase mDatabase;//数据库
    private Context mContext;//上下文
    private SQLiteMaster.DBOpenHelper mDBOpenHelper;//数据库打开帮助类

    /**
     * 数据表上下文初始化
     * @param context 上下文
     */
    public CollectionDBDao(Context context){
        mContext = context;;
    }

    /**
     * 设置该表从属的数据库
     * @param db 数据库名
     */
    public void setDatabase(SQLiteDatabase db){ mDatabase = db; }

    /**
     * 插入collection对象
     * @param Colletion collection对象
     * @return 插入数据的条数
     */
    public long insertData(collection Colletion) {
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //TODO 无法插入bitmap
        Colletion.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,Colletion.getUrl());
        values.put(KEY_TEXT,Colletion.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        values.put(KEY_LABEL,Colletion.getLabel());
        return mDatabase.insert(TABLE_NAME , null , values);
    }

    /**
     * 删除该表中的数据
     * @param id collection对象的数据库ID
     * @return 删除设局的条数
     */
    public long deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    /**
     * 删除该表下所有的数据
     * @return 删除设局的条数
     */
    public long deleteAllData() {
        return mDatabase.delete(TABLE_NAME, null, null);
    }

    /**
     * 更新该表下的数据
     * @param id 要更新的对象的数据库ID
     * @param Collection 更改后的collection对象
     * @return 更新的数据的条数
     */
    public long updateData(int id , collection Collection){
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Collection.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,Collection.getUrl());
        values.put(KEY_TEXT,Collection.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        values.put(KEY_LABEL,Collection.getLabel());
        return mDatabase.update(TABLE_NAME,values,KEY_ID + "=" + id , null);
    }

    /**
     * 查询一条数据
     * @param id 依据ID查找数据
     * @return collection对象链表
     */
    public List<collection> queryData(int id){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON,
                KEY_LABEL}, KEY_ID + "=" + id , null , null , null , null);
        return convertUtil(results);
    }

    /**
     * 查询所有的数据
     * @return collection对象链表
     */
    public List<collection> queryDataList(){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)) {return null;}
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON,
                KEY_LABEL}, null , null , null , null , null);
        return convertUtil(results);
    }

    /**
     * 将表通过Cursor查找到的数据转化为collection对象链表
     * @param cursor 表的游标
     * @return collection对象链表
     */
    @SuppressLint("Range")
    private List<collection> convertUtil(Cursor cursor){
        int resultCounts = cursor.getCount();
        if(resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        List<collection> mList = new ArrayList<>();
        for(int i = 0 ; i < resultCounts ; i ++){
            collection Collection = new collection();
            Collection.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            Collection.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
            Collection.setText(cursor.getString(cursor.getColumnIndex(KEY_TEXT)));
            byte[] in = cursor.getBlob(cursor.getColumnIndex(KEY_WEBICON));
            Bitmap bmpout = BitmapFactory.decodeByteArray(in , 0 , in.length);
            Collection.setWebIcon(bmpout);
            Collection.setLabel(cursor.getString(cursor.getColumnIndex(KEY_LABEL)));
            mList.add(Collection);
            cursor.moveToNext();
        }
        return mList;
    }
}
