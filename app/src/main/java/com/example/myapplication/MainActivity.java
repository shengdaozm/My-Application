package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.SQlite.*;
import com.firstpage.*;
import com.login.*;
public class MainActivity extends AppCompatActivity {

    private Button mainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG","--------------Begin----------------");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //声明控件
        mainBtn = (Button) findViewById(R.id.mainBtn);
    }

    public void begin(View view) {
        Log.d("TAG","---------------------点击按钮-----------------------");
        Intent intent= new Intent(com.example.myapplication.MainActivity.this, com.login.login.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}