package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

import com.publicClass.isFirst;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST","软件启动");
        super.onCreate(savedInstanceState);
        isFirst is;
        try {
            is =new isFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(is.getIsfirst()) {
            changeActivity(null);
        }

        setContentView(R.layout.activity_main);
        ViewPager viewpaper = findViewById(R.id.viewpager);
        ArrayList<View> view_list = new ArrayList<>();
        LayoutInflater li = getLayoutInflater();
        view_list.add(li.inflate(R.layout.view_1,null,false));
        view_list.add(li.inflate(R.layout.view_2,null,false));
        view_list.add(li.inflate(R.layout.view_3,null,false));
        view_list.add(li.inflate(R.layout.view_4,null,false));
        MsPagerAdapter mAdapter =  new MsPagerAdapter(view_list);
        viewpaper.setAdapter(mAdapter);
    }

    /**
     * 主页面的活动跳转
     */
    public void changeActivity(View view) {
        Intent intent= new Intent(com.example.myapplication.MainActivity.this,com.webpage.webpage.class);
        startActivity(intent);
        finish();
    }
}