package com.webpage;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

// //https://www.dmoe.cc/random.phpkaolv 使用随机图API
public class collectionShow extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "进入收藏的记录页面 !");
        setContentView(R.layout.collectios);
    }
}
