package com.SQlite;

import android.app.Application;

import android.util.Log;
import com.SQlite.SQLiteMaster;
import com.webpage.webpage;

public class MyApplication extends Application {

    /** 声明数据库操作实例 */
    public static SQLiteMaster mSQLiteMaster;

    public  MyApplication() throws IllegalAccessException, InstantiationException {
        Log.d("TEST","????");
        super.onCreate();
        //启动数据库
        SQLiteMaster mSQLiteMaster = new SQLiteMaster(webpage.class.newInstance());
        mSQLiteMaster.openDataBase();
    }
}
