package com.example.myapplication;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewpaper = (ViewPager) findViewById(R.id.viewpager);

        ArrayList<View> view_list = new ArrayList<View>();

        LayoutInflater li = getLayoutInflater();

        view_list.add(li.inflate(R.layout.view_one,null,false));
        view_list.add(li.inflate(R.layout.view_two,null,false));
        view_list.add(li.inflate(R.layout.view_three,null,false));

        MsPagerAdapter mAdapter =  new MsPagerAdapter(view_list);

        viewpaper.setAdapter(mAdapter);
    }
}