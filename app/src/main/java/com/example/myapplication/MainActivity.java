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
        setContentView(R.layout.activity_main);
        mainBtn = (Button) findViewById(R.id.mainBtn);
    }

    public void changeActivity(View view) {
        Intent intent= new Intent(com.example.myapplication.MainActivity.this, com.login.login.class);
        startActivity(intent);
    }
}