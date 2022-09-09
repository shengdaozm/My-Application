package com.example.myapplication;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.SQlite.*;
import com.example.myapplication.R;
import com.firstpage.*;
import com.login.*;
public class MainActivity extends AppCompatActivity {

    //声明控件
    private Button myBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }


}