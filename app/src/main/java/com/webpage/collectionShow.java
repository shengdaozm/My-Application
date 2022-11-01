package com.webpage;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.SQlite.SQLiteMaster;
import com.example.myapplication.R;
import com.publicClass.Labol;
import com.publicClass.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// //https://www.dmoe.cc/random.phpkaolv 使用随机图API
public class collectionShow extends AppCompatActivity {

    SQLiteMaster mSQLiteMaster;
    List<Labol> mLabols = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "进入收藏的记录页面 !");
        setContentView(R.layout.collectios);
    }

    public void getFromDB() throws IllegalAccessException, InstantiationException {
        mSQLiteMaster = new SQLiteMaster(collectionShow.this);
        mSQLiteMaster.openDataBase();
        mLabols = mSQLiteMaster.mLabolDBDao.queryDataList();
        Collections.reverse(mLabols);//反转mHistories 按照时间的新旧进行排序
    }

    public void onDestory(){
        mSQLiteMaster.closeDataBase();
    }
}
