package com.webpage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import com.SQlite.SQLiteMaster;
import com.example.myapplication.R;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.publicClass.history;
import com.publicClass.collection;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class webpage extends AppCompatActivity implements OnClickListener {

    private Set<String> hs;//用哈希进行判重
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final int PRESS_BACK_EXIT_GAP = 2000;
    private Context mContext;
    private InputMethodManager manager;
    private long exitTime = 0;
    private EditText textUrl;
    private ImageView webIcon;
    private ImageButton btnStart,btnHistory,btnDownload,btn_add_collection,btn_my_collections;
    private WebView webView;
    private WebSettings settings;
    private ProgressBar progressBar;
    private boolean is_have_image=true,is_add_history=true;
    SQLiteMaster mSQLiteMaster;
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;

    /**
     * 绑定控件
     */
    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        Log.d("TEST","界面控件初始化");
        //顶层网址控件
        webIcon = findViewById(R.id.webIcon);
        textUrl = findViewById(R.id.textUrl);
        btnStart = findViewById(R.id.btnStart);
        btnHistory = findViewById(R.id.btnhistory);
        btnDownload= findViewById(R.id.btn_download);
        btn_add_collection= findViewById(R.id.btn_add_collection);
        btn_my_collections= findViewById(R.id.btn_my_collections);
        //浏览器的进度条
        progressBar = findViewById(R.id.progressBar);
        //网页内容显示
        webView = findViewById(R.id.webView);
        //Switch控件
        Switch no_image = findViewById(R.id.no_image);
        Switch no_history = findViewById(R.id.no_history);

        // 地址输入栏获取与失去焦点处理
        textUrl.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                // 显示当前网址链接 TODO:搜索页面显示搜索词
                textUrl.setText(webView.getUrl());
                // 光标置于末尾
                textUrl.setSelection(textUrl.getText().length());
                // 显示因特网图标
                webIcon.setImageResource(R.drawable.ic_internet);
                // 显示跳转按钮
                btnStart.setImageResource(R.drawable.go);
            } else {
                // 显示网站名
                textUrl.setText(webView.getTitle());
                // 显示网站图标
                webIcon.setImageBitmap(webView.getFavicon());
                // 显示刷新按钮
                btnStart.setImageResource(R.drawable.refresh);
            }
        });

        // 监听键盘回车搜索
        textUrl.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                btnStart.callOnClick();
                textUrl.clearFocus();
            }
            return false;
        });

        no_history.setOnCheckedChangeListener((compoundButton, b) -> {
            is_add_history = !b;
            if (b) {
                toast("你的浏览不会被记录啦！");
            } else {
                toast("又要记录你的浏览啦！");
            }
        });
        no_image.setOnCheckedChangeListener((compoundButton, b) -> {
            is_have_image = !b;
            settings.setLoadsImagesAutomatically(is_have_image);
            if (b) {
                toast("进入无图模式,记得刷新哦");
            } else {
                toast("又可以看到图片啦,记得刷新哦");
            }
        });
        btnStart.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btn_add_collection.setOnClickListener(this);
        btn_my_collections.setOnClickListener(this);
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

        settings = webView.getSettings();
        // 启用 js 功能
        settings.setJavaScriptEnabled(true);
        // 设置浏览器 UserAgent
        settings.setUserAgentString(settings.getUserAgentString() + " mkBrowser/" + getVerName(webpage.this));
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
        settings.setLoadsImagesAutomatically(is_have_image);
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
            progressBar.setProgress(0); // 网页开始加载，显示进度条
            progressBar.setVisibility(View.VISIBLE);
            textUrl.setText("加载中..."); // 更新状态文字
            webIcon.setImageResource(R.drawable.internet); // 切换默认网页图标
            // TODO 数据库的基础判重需要做。
            if(is_add_history&&!hs.equals(url)) {
                hs.add(url);
                history h = new history(url , view.getTitle(), favicon==null?((BitmapDrawable)webIcon.getDrawable()).getBitmap():favicon);
                mSQLiteMaster.mHistoryDBDao.insertData(h);
            }
        }

        /**
         * 添加收藏的功能
         * @param view 网络布局的webview
         * @param url 存储的网址
         * @param favicon 网络的图标
         */
        public void addCollection(WebView view, String url, Bitmap favicon) {
            history h = new history(url , view.getTitle(), favicon==null?((BitmapDrawable)webIcon.getDrawable()).getBitmap():favicon);
            mSQLiteMaster.mHistoryDBDao.insertData(h);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 网页加载完毕，隐藏进度条
            progressBar.setVisibility(View.INVISIBLE);
            // 改变标题
            setTitle(webView.getTitle());
            // 显示页面标题
            textUrl.setText(webView.getTitle());
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
     * webpage界面生成
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST","界面初始化");
        super.onCreate(savedInstanceState);

        hs= new HashSet<>();

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);
        }

        mSQLiteMaster = new SQLiteMaster(com.webpage.webpage.this);
        mSQLiteMaster.openDataBase();

        setContentView(R.layout.webpage);
        mContext = webpage.this;
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        initView();
        initWeb();
        Intent i = getIntent();
        String load_url = i.getStringExtra("load_url");
        if(load_url==null) {
            toast("欢迎使用！祝您快乐每一天！");
            toast("右滑可以打开设置栏！");
        } else {
            Log.d("TEST","url from history is loaded!");
            webView.loadUrl(load_url);
        }
    }

    /**
     * 返回按钮处理
     */
    @Override
    public void onBackPressed() {
        // 能够返回则返回上一页
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > PRESS_BACK_EXIT_GAP) {
                // 连点两次退出程序
                Toast.makeText(mContext, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int ID = view.getId();
        if (ID == R.id.btnStart) {
            Log.d("TEST","btnStart  is on!");
            if (textUrl.hasFocus()) { // 隐藏软键盘
                if (manager.isActive())
                    manager.hideSoftInputFromWindow(textUrl.getApplicationWindowToken(), 0);
                // 地址栏有焦点，是跳转
                String input = textUrl.getText().toString();
                if (!isHttpUrl(input)) {// 不是网址，加载搜索引擎处理
                    try {// URL 编码
                        input = URLEncoder.encode(input, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    input = "https://www.baidu.com/s?wd=" + input + "&ie=UTF-8";
                }
                webView.loadUrl(input);
                // 取消掉地址栏的焦点
                textUrl.clearFocus();
            } else // 地址栏没焦点，是刷新
                webView.reload();
        } else if(ID==R.id.btnhistory) { //历史界面
            Log.d("TEST","btn_history is on !");
            Intent intent= new Intent(com.webpage.webpage.this, historyShow.class);
            startActivity(intent);
        } else if(ID==R.id.btn_download) {
            try {
                new download(webView.getUrl());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            toast("页面下载完成，请移步到软件的文件夹下进行查看！");
        } else if(ID==R.id.btn_add_collection) {
            final String[] collection_label = new String[1];
            AlertDialog.Builder builder = new AlertDialog.Builder(webpage.this);
            builder.setIcon(R.drawable.add_collections);
            builder.setTitle("请输入当前页面所属分类");
            //  通过LayoutInflater来加载一个xml的布局文件作为一个View对象
            View viewx = LayoutInflater.from(webpage.this).inflate(R.layout.report_collection, null);
            //  设置我们自己定义的布局文件作为弹出框的Content
            builder.setView(viewx);

            final EditText label = viewx.findViewById(R.id.username);
            builder.setPositiveButton("确定", (dialog, which) -> collection_label[0] = label.getText().toString().trim());
            builder.setNegativeButton("取消", (dialog, which) -> {});
            builder.show();
            // 操作完成后，就需要进行将该条记录写入数据库。
            collection c=new collection(webView.getUrl(),webView.getTitle(), webView.getFavicon()==null?((BitmapDrawable)webIcon.getDrawable()).getBitmap():webView.getFavicon(),collection_label[0]);
            // TODO 将这条记录插入数据库

        } else if(ID==R.id.btn_my_collections) {
            Intent intent= new Intent(com.webpage.webpage.this, collectionShow.class);
            startActivity(intent);
        }
    }

    /**
     * 判断字符串是否为URL
     * @param urls 要勘定的字符串
     * @return true:是URL、false:不是URL
     */
    public static boolean isHttpUrl(String urls) {
        boolean isUrl;
        // 判断是否是网址的正则表达式
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";
        Pattern pat = Pattern.compile(regex.trim());
        Matcher mat = pat.matcher(urls.trim());
        isUrl = mat.matches();
        return isUrl;
    }

    private void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库
        mSQLiteMaster.closeDataBase();
    }
}