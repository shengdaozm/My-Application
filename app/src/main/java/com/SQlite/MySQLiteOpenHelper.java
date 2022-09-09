package com.SQlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.publicClass.User;

/**
 *
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "mySQLite.db";
    private static final String TABLE_NAME_USER = "user";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private static final String MAIL = "mail";
    private static final String PASSWORD = "password";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_USER + " (id integer primary key autoincrement, name text, number text, mail text, password text);";

    //构造函数
    public MySQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    //第一次创建数据库时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    //数据库升级调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //返回值-1插入失败
    public long insertData(User user){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME , user.getName());
        values.put(NUMBER , user.getPhoneNumber());
        values.put(MAIL , user.getMail());
        values.put(PASSWORD , user.getPassword());

        return db.insert(TABLE_NAME_USER , null , values);
    }


    //返回删除元素的个数
    public int deleteFromDBByName(String name){

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(TABLE_NAME_USER, "name like ?" , new String[] {name});

    }

    //更改的行数
    public int updateData(User user){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME , user.getName());
        values.put(NUMBER , user.getPhoneNumber());
        values.put(MAIL , user.getMail());
        values.put(PASSWORD , user.getPassword());

        return db.update(TABLE_NAME_USER, values,"name like ?",new String[] {user.getName()});
    }

    //查
    public User[] queryFromDBByName(String name){

        SQLiteDatabase db = getWritableDatabase();

        //需要解决  数组的大小已经被提前确定
        User[] usersList = new User[100];
        //List<User> usersList = new List<>();

        Cursor cursor = db.query(TABLE_NAME_USER, null,"name like ?",new String[]{name},null,null, null);
        //2参返回的数据的种类，如果使用：new String[]{"name","number"}; null 所有都要
        //5参分组，7参排序方式

        if (cursor != null){

            while(cursor.moveToNext()){

                @SuppressLint("Range") String name1 = cursor.getString(cursor.getColumnIndex(NUMBER));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(NUMBER));
                @SuppressLint("Range") String mail = cursor.getString(cursor.getColumnIndex(MAIL));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(PASSWORD));

                User user = new User();
                user.setName(name1);
                user.setPhoneNumber(number);
                user.setMail(mail);
                user.setPassword(password);

                //usersList.append(user);
            }

            cursor.close();
        }

        return usersList;
    }
}
