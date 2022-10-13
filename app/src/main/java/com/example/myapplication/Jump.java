package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.wear.tiles.material.Button;
import com.example.myapplication.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Jump extends AppCompatActivity {

    private ImageButton btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leftmenu);

        // 获取控件id
        btn1=findViewById(R.id.btnFav);

        // 监听点击事件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到另一个名为ButtonActivity的界面
                Intent intent=new Intent(Jump.this,ButtonActivity.class);
                startActivity(intent);
            }
        });

    }
}



