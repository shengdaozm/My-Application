package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import com.webpage.historyShow;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button btnAllinall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST","软件启动");
        super.onCreate(savedInstanceState);
        btnAllinall=(Button) findViewById(R.id.all_in_all);
//        btnAllinall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(com.example.myapplication.MainActivity.this,com.webpage.webpage.class);
//                startActivity(intent);
//            }
//        });
        setContentView(R.layout.activity_main);

        ViewPager viewpaper = (ViewPager) findViewById(R.id.viewpager);

        ArrayList<View> view_list = new ArrayList<View>();

        LayoutInflater li = getLayoutInflater();

        view_list.add(li.inflate(R.layout.view_one,null,false));
        view_list.add(li.inflate(R.layout.view_two,null,false));
        view_list.add(li.inflate(R.layout.view_three,null,false));
        //view_list.add(li.inflate(R.layout.view_forth,null,false));

        MsPagerAdapter mAdapter =  new MsPagerAdapter(view_list);
        viewpaper.setAdapter(mAdapter);
    }

    /**
     * 主页面的活动跳转
     * @param is 判定是否是第一次的进入软件
     */
    public void changeActivity(Boolean is) {
        Intent intent= new Intent(com.example.myapplication.MainActivity.this,is?com.login.login.class:com.webpage.webpage.class);
        startActivity(intent);
    }


}