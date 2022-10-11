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

//用户数据表操作类
public class HistoryDBDao {
    //数据库名称
    public static final String TABLE_NAME = "history_info";

    //表的字段名
    public static String KEY_ID = "id";
    public static String KEY_URL = "url";
    public static String KEY_TEXT = "text";
    public static String KEY_WEBICON = "webIcon";

    private SQLiteDatabase mDatabase;

    private Context mContext;
    private SQLiteMaster.DBOpenHelper mDBOpenHelper;

    public HistoryDBDao(Context context){ mContext = context; }
    public void setDatabase(SQLiteDatabase db){ mDatabase = db; }

    //插入一条数据
    public long insertData(history History) {
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        History.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,History.getUrl());
        values.put(KEY_TEXT,History.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        return mDatabase.insert(TABLE_NAME , null , values);
    }

    //更新一条数据
    public long updateData(String url , history History){
        ContentValues values = new ContentValues();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        History.getWebIcon().compress(Bitmap.CompressFormat.PNG,100,os);
        values.put(KEY_URL,History.getUrl());
        values.put(KEY_TEXT,History.getText());
        values.put(KEY_WEBICON,os.toByteArray());
        return mDatabase.update(TABLE_NAME,values,KEY_URL + "=" + url , null);
    }

    //查询一条数据

    /**
     *
     * @param url  用户昵称
     * @return
     */
    public List<history> queryData(String url){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_URL,
                KEY_TEXT,
                KEY_WEBICON}, KEY_URL + "=" + url , null , null , null , null);
        return convertUtil(results);
    }

    //查询所有数据
    public List<history> queryDataList(){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
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
            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
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
