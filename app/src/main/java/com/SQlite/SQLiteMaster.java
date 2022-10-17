package com.SQlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.publicClass.User;
import com.publicClass.history;
import com.SQlite.SQLiteConfig;

import static com.SQlite.SQLiteConfig.DB_NAME;
import static com.SQlite.SQLiteConfig.DB_VERSION;

public class SQLiteMaster {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBOpenHelper mDBOpenHelper;

    //数据表操作类实例化
    public UserDBDao mUserDBDao;
    public HistoryDBDao mHistoryDBDao;
    public CollectionDBDao mCollectionDBDao;

    public SQLiteMaster(Context context){
        mContext = context;
        mUserDBDao = new UserDBDao(mContext);
        mHistoryDBDao = new HistoryDBDao(mContext);
        mCollectionDBDao = new CollectionDBDao(mContext);
        Log.d("TEST","SQLiteMaster构造成功");
    }

    //打开数据库
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

    //关闭数据库
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

    private static final String mCollectionSqlqtr = "create table if not exists " + CollectionDBDao.TABLE_NAME + "(" +
            HistoryDBDao.KEY_ID + " integer primary key autoincrement , " +
            HistoryDBDao.KEY_URL + " text not null , " +
            HistoryDBDao.KEY_TEXT + " text not null , " +
            HistoryDBDao.KEY_WEBICON + " BLOB );";

    //删除该数据库下User表的语句
    private static final String mUserDelSql = "DROP TABLE IF EXISTS " + UserDBDao.TABLE_NAME;

    //删除该数据库下History表的语句
    private static final String mHistoryDelSql = "DROP TABLE IF EXISTS " + HistoryDBDao.TABLE_NAME;
    //删除该数据库下Collection表的语句
    private static final String getCollectionDelSql = "DROP TABLE IF EXISTS " + HistoryDBDao.TABLE_NAME;
    //数据表打开帮助类
    public static class DBOpenHelper extends SQLiteOpenHelper{

        public DBOpenHelper(Context context , String name , SQLiteDatabase.CursorFactory factory , int version){
            super(context , name , factory , version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(mUserSqlStr);
            db.execSQL(mHistorySqlqtr);
            db.execSQL(mCollectionSqlqtr);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(mUserDelSql);
            db.execSQL(mHistoryDelSql);
            db.execSQL(mCollectionSqlqtr);
            onCreate(db);
        }
    }
}
