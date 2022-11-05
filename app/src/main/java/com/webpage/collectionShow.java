package com.webpage;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.SQlite.SQLiteMaster;
import com.example.myapplication.R;
import com.publicClass.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// //https://www.dmoe.cc/random.phpkaolv 使用随机图API
public class collectionShow extends AppCompatActivity {

    SQLiteMaster mSQLiteMaster;
    List<collection> mCollections = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "进入收藏的记录页面 !");
        setContentView(R.layout.collectios);
    }

    public void getFromDB() throws IllegalAccessException, InstantiationException {
        mSQLiteMaster = new SQLiteMaster(collectionShow.this);
        mSQLiteMaster.openDataBase();
        mCollections = mSQLiteMaster.mCollectionDBDao.queryDataList();
        Collections.reverse(mCollections);//反转mCollections 按照时间的新旧进行排序
    }

    public void onDestory(){
        mSQLiteMaster.closeDataBase();
    }
}
