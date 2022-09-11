package com.firstpage;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.SQlite.MySQLiteOpenHelper;
import com.example.myapplication.R;

public class firstpage extends AppCompatActivity implements OnClickListener {
    private String url;

    private EditText texturl;
    private ImageView btnStart;
    private WebView Web;
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
        setContentView(R.layout.firstpage_main);
        btnStart = (ImageView) findViewById(R.id.btnStart);
        btnback = (ImageView) findViewById(R.id.goBack);
        texturl = (EditText) findViewById(R.id.textUrl);
        btnGo = (ImageView) findViewById(R.id.goForward);
        btnSettings = (ImageView) findViewById(R.id.navSet);
        btnGohome = (ImageView) findViewById(R.id.goHome);
        Web=(WebView) findViewById(R.id.webView);
    }

    // TODO 这个部分暂时为了测试，先不写线程
    /**
     * 连接网络，并将网页发送到webview，需要注意的是，每一个网页应该是一个线程，支持多网页的打开并浏览
     */
    public void connectIntnet() {
        //网址的预处理
        //TODO 考虑优化异常处理的逻辑，用户输入网址的时候，直接进行访问；用户输入的是中文，直接加工成url百度的网址进行访问
        url= String.valueOf(texturl.getText());
        Log.d("TAG",url+"###############################################");
        if(url.equals("")) return;
        url= "https://" +url;
        url=url.replace(" ","");
        Web.loadUrl(url);
        Web.getSettings().setUseWideViewPort(true);
        Web.getSettings().setLoadWithOverviewMode(true);

        Log.d("TAG","#######################浏览器界面测试##########################");
        WebSettings settings = Web.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        Web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
    }


    /**
     * firstpage主界面的点击响应
     * @param view 界面的响应
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                connectIntnet();
                break;
            case R.id.goBack:
                //退回上一个网页
                break;
            case R.id.goForward:

                break;
            case R.id.goHome:

                break;
        }
    }
}
