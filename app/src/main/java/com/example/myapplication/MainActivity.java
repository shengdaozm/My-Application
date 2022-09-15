package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.publicClass.isFirst;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button mainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //声明控件
        mainBtn = (Button) findViewById(R.id.mainBtn);
        isFirst isJump;
        try {
            isJump = new isFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (isJump.getIsfirst()) {
            Intent intent= new Intent(com.example.myapplication.MainActivity.this, com.firstpage.firstpage.class);
            startActivity(intent);
        }
    }

    public void begin(View view) {
        Intent intent= new Intent(com.example.myapplication.MainActivity.this, com.login.login.class);
        startActivity(intent);
    }
}