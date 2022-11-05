package com.SQlite;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.publicClass.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据表操作类
 */
public class UserDBDao {
    public static final String TABLE_NAME = "user_info";//数据表名称

    public static String KEY_ID = "id";//数据库自增ID
    public static String KEY_NAME = "name";//用户名
    public static String KEY_MAIL = "mail";//用户邮箱
    public static String KEY_PASSWORD = "password";//用户密码
    private SQLiteDatabase mDatabase;//数据库
    private Context mContext;//上下文
    private SQLiteMaster.DBOpenHelper mDBOpenHelper;//数据库打开帮助类

    /**
     * 设置该数据表的上下文
     * @param context 上下文
     */
    public UserDBDao(Context context){ mContext = context; }

    /**
     * 设置该表从属的数据库
     * @param db 数据库名
     */
    public void setDatabase(SQLiteDatabase db){ mDatabase = db; }

    /**
     * 插入数据
     * @param user User对象
     * @return 插入数据的条数
     */
    public long insertData(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,user.getName());
        values.put(KEY_MAIL,user.getMail());
        values.put(KEY_PASSWORD,user.getPassword());
        return mDatabase.insert(TABLE_NAME , null , values);
    }

    /**
     * 删除数据
     * @param id 用户数据库的ID
     * @return 删除数据的条数
     */
    public long deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    /**
     * 删除所有数据
     * @return 删除的数据的条数
     */
    public long deleteAllData() {
        return mDatabase.delete(TABLE_NAME, null, null);
    }

    /**
     * 更新一条数据
     * @param id 要更新数据的ID
     * @param user 更新后的User对象
     * @return 更新数据的条数
     */
    public long updateData(int id , User user){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,user.getName());
        values.put(KEY_MAIL,user.getMail());
        values.put(KEY_PASSWORD,user.getPassword());
        return mDatabase.update(TABLE_NAME,values,KEY_ID + "=" + id , null);
    }

    /**
     * 查询一条数据
     * @param id  用户自增ID
     * @return User链表，存储User对象
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

    /**
     * 查询所有的数据
     * @return 包含被查询数据的User链表
     */
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

    /**
     * 将查询结果进行转换
     * @param cursor 读取该数据表的游标
     * @return 包含被查询数据的User链表
     */
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
