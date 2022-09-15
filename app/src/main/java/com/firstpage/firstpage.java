package com.firstpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

//https://github.com/zhangbenzhi/Mkbrowser-master
public class firstpage extends AppCompatActivity implements OnClickListener {
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final int PRESS_BACK_EXIT_GAP = 2000;
    private String url;
    private EditText textUrl;
    private ImageView btnStart, btnback, btnGo, btnSettings, btnNewpage, btnGohome, webIcon;
    private WebView webView;
    private ProgressBar progressBar;
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    /**
     * 绑定控件
     */
    public void initView() {
        //顶层网址控件
        webIcon = (ImageView) findViewById(R.id.webIcon);
        textUrl = (EditText) findViewById(R.id.textUrl);
        btnStart = (ImageView) findViewById(R.id.btnStart);
        //浏览器的进度条
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //网页内容显示
        webView = (WebView) findViewById(R.id.webView);
        //底层功能控件
        btnback = (ImageView) findViewById(R.id.goBack);
        btnGo = (ImageView) findViewById(R.id.goForward);
        btnNewpage = (ImageView) findViewById(R.id.newPage);
        btnSettings = (ImageView) findViewById(R.id.navSet);
        btnGohome = (ImageView) findViewById(R.id.goHome);
        webView = (WebView) findViewById(R.id.webView);
    }

    /**
     * 初始化 web
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWeb() {
        // 重写 WebViewClient
        webView.setWebViewClient(new MkWebViewClient());
        // 重写 WebChromeClient
        webView.setWebChromeClient(new MkWebChromeClient());

        WebSettings settings = webView.getSettings();
        // 启用 js 功能
        settings.setJavaScriptEnabled(true);
        // 设置浏览器 UserAgent
        settings.setUserAgentString(settings.getUserAgentString() + " mkBrowser/" + getVerName(firstpage.this));
        // 将图片调整到适合 WebView 的大小
        settings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        // 支持缩放，默认为true。是下面那个的前提。
        settings.setSupportZoom(true);
        // 设置内置的缩放控件。若为false，则该 WebView 不可缩放
        settings.setBuiltInZoomControls(true);
        // 隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 设置可以访问文件
        settings.setAllowFileAccess(true);
        // 支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 支持自动加载图片
        // TODO 在此处可以考虑加入无图浏览功能
        settings.setLoadsImagesAutomatically(true);
        // 设置默认编码格式
        settings.setDefaultTextEncodingName("utf-8");
        // 本地存储
        settings.setDomStorageEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);

        // 资源混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 加载首页 主页不设置加载历史
        webView.loadUrl(getResources().getString(R.string.home_url));
    }

    /**
     * 获取版本号名称
     * @param context 上下文
     * @return 当前版本名称
     */
    private static String getVerName(Context context) {
        String verName ="unKnow" ;
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    // TODO 这个部分暂时为了测试，先不写线程

    /**
     * firstpage主界面的点击响应
     *
     * @param view 界面的响应
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                String url= String.valueOf(textUrl.getText());
                webView.loadUrl("https://"+url);
                break;
            case R.id.goBack:
                //退回上一个网页
                break;
            case R.id.goForward:
                //回到下一个网页
                break;
            case R.id.goHome:
                webView.loadUrl(String.valueOf(R.string.home_url));
                //回到主菜单
                break;
        }
    }

    /**
     * 重写 WebViewClient
     */
    private class MkWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            if (url == null) {
                // 返回true自己处理，返回false不处理
                return true;
            }
            // 正常的内容，打开
            if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
                view.loadUrl(url);
                return true;
            }
            // 调用第三方应用，防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            try {
                // TODO:弹窗提示用户，允许后再调用
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivityForResult(intent, 100);
                return true;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // 网页开始加载，显示进度条
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
            // 更新状态文字
            textUrl.setText("加载中...");
            // 切换默认网页图标
            webIcon.setImageResource(R.drawable.internet);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 网页加载完毕，隐藏进度条
            progressBar.setVisibility(View.INVISIBLE);
            //nowUrl = url;
            //添加浏览记录
            //BrowseUtil.browse(WebActivity.this, url);
            // 改变标题
            setTitle(webView.getTitle());
            // 显示页面标题
            textUrl.setText(webView.getTitle());

            //设置收藏与取消收藏
            //collection.setActivated(CollectionUtil.isCollected(WebActivity.this, url));
        }
    }

    /**
     * 重写 WebChromeClient
     */
    private class MkWebChromeClient extends WebChromeClient {
        private final static int WEB_PROGRESS_MAX = 100;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 加载进度变动，刷新进度条
            progressBar.setProgress(newProgress);
            if (newProgress > 0)
                if (newProgress == WEB_PROGRESS_MAX)
                    progressBar.setVisibility(View.INVISIBLE);
                else
                    progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            // 改变图标
            webIcon.setImageBitmap(icon);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            // 改变标题
            setTitle(title);
            // 显示页面标题
            textUrl.setText(title);
        }
    }

    /**
     * firstpage界面生成
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage_main);
        initView();
        initWeb();
    }
}