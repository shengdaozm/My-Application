package com.firstpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class firstpage extends AppCompatActivity implements OnClickListener {
    private String url;

    private EditText texturl;
    private ImageView btnStart;
    private ImageView btnback;
    private ImageView btnGo;
    private ImageView btnSettings;
    //???
    //private ImageView btnNewpage= (ImageView) findViewById(R.id.newPage);
    private ImageView btnGohome;

    private MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnStart = (ImageView) findViewById(R.id.btnStart);
        btnback = (ImageView) findViewById(R.id.goBack);
        texturl = (EditText) findViewById(R.id.textUrl);
        btnGo = (ImageView) findViewById(R.id.goForward);
        btnSettings = (ImageView) findViewById(R.id.navSet);
        btnGohome = (ImageView) findViewById(R.id.goHome);
    }

    /**
     * 连接网络，并将网页发送到webview，需要注意的是，每一个网页应该是一个线程，支持多网页的打开并浏览
     * @param url 需要连接的网址
     */
    public void connectIntnet(String url) {

    }


    /**
     * firstpage主界面的点击响应
     * @param view
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                url= String.valueOf(texturl.getText());
                if(url.equals("")) {
                    //考虑优化异常处理的逻辑
                    return;
                }
                url= "https://" +url;
                url=url.replace(" ","");
                connectIntnet(url);
                break;
            case R.id.goBack:

                break;
            case R.id.goForward:

                break;
            case R.id.goHome:

                break;
        }
    }
}
