package com.SQlite;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.publicClass.User;

import java.util.ArrayList;
import java.util.List;

//用户数据表操作类
public class UserDBDao {
    //数据库名称
    public static final String TABLE_NAME = "user_info";

    //表的字段名
    //数据库自增ID
    public static String KEY_ID = "id";
    //表的字段名
    public static String KEY_NAME = "name";
    public static String KEY_MAIL = "mail";
    public static String KEY_PASSWORD = "password";
    //数据库
    private SQLiteDatabase mDatabase;
    //上下文
    private Context mContext;
    //数据库打开帮助类
    private SQLiteMaster.DBOpenHelper mDBOpenHelper;

    public UserDBDao(Context context){ mContext = context; }
    public void setDatabase(SQLiteDatabase db){ mDatabase = db; }

    //插入一条数据
    public long insertData(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,user.getName());
        values.put(KEY_MAIL,user.getMail());
        values.put(KEY_PASSWORD,user.getPassword());
        return mDatabase.insert(TABLE_NAME , null , values);
    }

    //删除一条数据
    public long deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    public long deleteAllData() {
        return mDatabase.delete(TABLE_NAME, null, null);
    }

    //更新一条数据
    public long updateData(int id , User user){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,user.getName());
        values.put(KEY_MAIL,user.getMail());
        values.put(KEY_PASSWORD,user.getPassword());
        return mDatabase.update(TABLE_NAME,values,KEY_ID + "=" + id , null);
    }

    //查询一条数据

    /**
     *
     * @param id  用户昵称
     * @return
     */
    public List<User> queryData(int id){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                        KEY_NAME,
                        KEY_MAIL,
                        KEY_PASSWORD}, KEY_ID + "=" + id , null , null , null , null);
        return convertUtil(results);
    }

    //查询所有数据
    public List<User> queryDataList(){
        if(!SQLiteConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID ,
                KEY_NAME,
                KEY_MAIL,
                KEY_PASSWORD}, null , null , null , null , null);
        return convertUtil(results);
    }

    //查询结果转换
    @SuppressLint("Range")
    private List<User> convertUtil(Cursor cursor){
        int resultCounts = cursor.getCount();
        if(resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        List<User> mList = new ArrayList<>();
        for(int i = 0 ; i < resultCounts ; i ++){
            User user = new User();
            user.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            user.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.setMail(cursor.getString(cursor.getColumnIndex(KEY_MAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            mList.add(user);
            cursor.moveToNext();
        }
        return mList;
    }
}
