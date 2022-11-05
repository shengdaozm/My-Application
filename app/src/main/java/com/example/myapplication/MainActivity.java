package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import com.example.myapplication.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST","软件启动");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实机演示请将该部分去掉
        //%%%%%%%%%%%%%%%%%
        //%%%%%%%%%%%%%%%%%
        changeActivity(false);
        //%%%%%%%%%%%%%%%%%
        //%%%%%%%%%%%%%%%%%
        ViewPager viewpaper = findViewById(R.id.viewpager);
        ArrayList<View> view_list = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        Boolean whouse=true;
        if(whouse) {
            view_list.add(li.inflate(R.layout.view_1,null,false));
            view_list.add(li.inflate(R.layout.view_2,null,false));
            view_list.add(li.inflate(R.layout.view_3,null,false));
            view_list.add(li.inflate(R.layout.view_4,null,false));
        } else {
            view_list.add(li.inflate(R.layout.view_one,null,false));
            view_list.add(li.inflate(R.layout.view_two,null,false));
            view_list.add(li.inflate(R.layout.view_three,null,false));
        }
        MsPagerAdapter mAdapter =  new MsPagerAdapter(view_list);
        viewpaper.setAdapter(mAdapter);
    }

    /**
     * 主页面的活动跳转
     * @param is 判定是否是第一次进入软件
     */
    public void changeActivity(Boolean is) {
        Intent intent= new Intent(com.example.myapplication.MainActivity.this,com.webpage.webpage.class);
        startActivity(intent);
    }
}