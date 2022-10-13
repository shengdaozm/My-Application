package com.SQlite;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.publicClass.history;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

//收藏数据表操作类
public class CollectionDBDao {
    //数据库名称
    public static final String TABLE_NAME = "collection_info";

    //数据库自增ID
    public static String KEY_ID = "id";
    //表的字段名
    public static String KEY_URL = "url";
    public static String KEY_TEXT = "text";
    public static String KEY_WEBICON = "webIcon";

    private SQLiteDatabase mDatabase;
    //上下文
    private Context mContext;
    //数据库打开帮助类
    private SQLiteMaster.DBOpenHelper mDBOpenHelper;

    public CollectionDBDao(Context context){
        mContext = context;;
    }

    public void setDatabase(SQLiteDatabase db){ mDatabase = db; }

    //插入一条数据
    public long insertData(history History) {
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //TODO 无法插入bitmap
        //History.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,History.getUrl());
        values.put(KEY_TEXT,History.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        return mDatabase.insert(TABLE_NAME , null , values);
    }

    //删除数据
    public long deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    public long deleteAllData() {
        return mDatabase.delete(TABLE_NAME, null, null);
    }

    //更新一条数据
    public long updateData(int id , history History){
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        History.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,History.getUrl());
        values.put(KEY_TEXT,History.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        return mDatabase.update(TABLE_NAME,values,KEY_ID + "=" + id , null);
    }

    //查询一条数据

    /**
     *
     * @param id
     * @return
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

    //查询所有数据
    public List<history> queryDataList(){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)) {return null;}
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON}, null , null , null , null , null);
        return convertUtil(results);
    }

    //查询结果转换
    @SuppressLint("Range")
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
