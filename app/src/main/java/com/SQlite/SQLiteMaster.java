package com.SQlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.SQlite.SQLiteConfig.DB_NAME;
import static com.SQlite.SQLiteConfig.DB_VERSION;

public class SQLiteMaster {

    private Context mContext;//上下文
    private SQLiteDatabase mDatabase;//数据库
    private DBOpenHelper mDBOpenHelper;//数据库打开帮助类
    public UserDBDao mUserDBDao;//数据表操作类实例化
    public HistoryDBDao mHistoryDBDao;//数据表操作类实例化
    public CollectionDBDao mCollectionDBDao;//数据表操作类实例化

    /**
     * 舒适化数据库上下文
     * @param context 上下文
     */
    public SQLiteMaster(Context context){
        mContext = context;
        mUserDBDao = new UserDBDao(mContext);
        mHistoryDBDao = new HistoryDBDao(mContext);
        mCollectionDBDao = new CollectionDBDao(mContext);
        Log.d("TEST","SQLiteMaster构造成功");
    }

    /**
     * 打开数据库，在super()方法调用
     */
    public void openDataBase(){
        mDBOpenHelper = new DBOpenHelper(mContext , DB_NAME , null , DB_VERSION);
        try{
            mDatabase = mDBOpenHelper.getWritableDatabase();//获取可写数据库
        } catch(SQLException e){
            mDatabase = mDBOpenHelper.getReadableDatabase();//获取只读数据库
        }
        //设置数据库的SQLiteDatabase
        mUserDBDao.setDatabase(mDatabase);
        mHistoryDBDao.setDatabase(mDatabase);
        mCollectionDBDao.setDatabase(mDatabase);
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase(){
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    //创建该数据库下User表的语句
    private static final String mUserSqlStr = "create table if not exists " + UserDBDao.TABLE_NAME + "(" +
            UserDBDao.KEY_ID + " integer primary key autoincrement , " +
            UserDBDao.KEY_NAME + " text not null , " +
            UserDBDao.KEY_MAIL + " text not null , " +
            UserDBDao.KEY_PASSWORD + " text );";

    //创建该数据库下History表的语句
    private static final String mHistorySqlqtr = "create table if not exists " + HistoryDBDao.TABLE_NAME + "(" +
            HistoryDBDao.KEY_ID + " integer primary key autoincrement , " +
            HistoryDBDao.KEY_URL + " text not null , " +
            HistoryDBDao.KEY_TEXT + " text not null , " +
            HistoryDBDao.KEY_WEBICON + " blob not null );";

    //创建该数据库下Collection表的语句
    private static final String mCollectionSqlqtr = "create table if not exists " + CollectionDBDao.TABLE_NAME + "(" +
            CollectionDBDao.KEY_ID + " integer primary key autoincrement , " +
            CollectionDBDao.KEY_URL + " text not null , " +
            CollectionDBDao.KEY_TEXT + " text not null , " +
            CollectionDBDao.KEY_WEBICON + " blob not null, " +
            CollectionDBDao.KEY_LABEL + " text not null );";


    //删除该数据库下User表的语句
    private static final String mUserDelSql = "DROP TABLE IF EXISTS " + UserDBDao.TABLE_NAME;

    //删除该数据库下History表的语句
    private static final String mHistoryDelSql = "DROP TABLE IF EXISTS " + HistoryDBDao.TABLE_NAME;
    //删除该数据库下Collection表的语句
    private static final String mCollectionDelSql = "DROP TABLE IF EXISTS " + CollectionDBDao.TABLE_NAME;
    //数据表打开帮助类
    public static class DBOpenHelper extends SQLiteOpenHelper{

        public DBOpenHelper(Context context , String name , SQLiteDatabase.CursorFactory factory , int version){
            super(context , name , factory , version);
        }

        /**
         * 创建数据库下的所有数据表
         * @param db 数据库
         */
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(mUserSqlStr);
            db.execSQL(mHistorySqlqtr);
            db.execSQL(mCollectionSqlqtr);
        }

        /**
         * 更新数据库版本时调用
         * @param db 数据库
         * @param i 前一个版本
         * @param i1 新版本
         */
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(mUserDelSql);
            db.execSQL(mHistoryDelSql);
            db.execSQL(mCollectionSqlqtr);
            onCreate(db);
        }
    }
}
