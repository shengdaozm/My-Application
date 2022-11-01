package com.SQlite;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.publicClass.Labol;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

//收藏数据表操作类
public class LabolDBDao {
    //数据库名称
    public static final String TABLE_NAME = "Labol_info";

    //数据库自增ID
    public static String KEY_ID = "id";
    //表的字段名
    public static String KEY_URL = "url";
    public static String KEY_TEXT = "text";
    public static String KEY_WEBICON = "webIcon";

    public static String KEY_LABOL = "labol";

    private SQLiteDatabase mDatabase;
    //上下文
    private Context mContext;
    //数据库打开帮助类
    private SQLiteMaster.DBOpenHelper mDBOpenHelper;

    public LabolDBDao(Context context){
        mContext = context;;
    }

    public void setDatabase(SQLiteDatabase db){ mDatabase = db; }

    //插入一条数据
    public long insertData(Labol labol) {
        Long id = null;
        ContentValues values = new ContentValues();
        Log.d("TEST","3");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Log.d("TEST","4");
        if(labol.getWebIcon() != null) {
            labol.getWebIcon().compress(Bitmap.CompressFormat.PNG, 100, os);
        }
        else os = null;
        Log.d("TEST","5");
        values.put(KEY_URL,labol.getUrl());
        values.put(KEY_TEXT,labol.getText());
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

    //删除数据
    public long deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    public long deleteAllData() {
        return mDatabase.delete(TABLE_NAME, null, null);
    }

    //更新一条数据
    public long updateData(int id , Labol labol){
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        labol.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,labol.getUrl());
        values.put(KEY_TEXT,labol.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        values.put(KEY_LABOL,labol.getL());
        return mDatabase.update(TABLE_NAME,values,KEY_ID + "=" + id , null);
    }

    //查询一条数据

    /**
     *
     * @param id
     * @return
     */
    public List<Labol> queryData(int id){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON,
                KEY_LABOL}, KEY_ID + "=" + id , null , null , null , null);
        return convertUtil(results);
    }

    //查询所有数据
    public List<Labol> queryDataList(){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)) {return null;}
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON,
                KEY_LABOL}, null , null , null , null , null);
        return convertUtil(results);
    }

    //查询结果转换
    @SuppressLint("Range")
    private List<Labol> convertUtil(Cursor cursor){
        int resultCounts = cursor.getCount();
        if(resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        List<Labol> mList = new ArrayList<>();
        for(int i = 0 ; i < resultCounts ; i ++){
            Labol labol = new Labol();
            labol.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            labol.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
            labol.setText(cursor.getString(cursor.getColumnIndex(KEY_TEXT)));
            byte[] in = cursor.getBlob(cursor.getColumnIndex(KEY_WEBICON));
            Bitmap bmpout = BitmapFactory.decodeByteArray(in , 0 , in.length);
            labol.setWebIcon(bmpout);
            labol.setL(cursor.getString(cursor.getColumnIndex(KEY_LABOL)));
            mList.add(labol);
            cursor.moveToNext();
        }
        return mList;
    }
}
