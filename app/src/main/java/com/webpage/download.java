package com.webpage;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import  com.publicClass.Filename;

public class download {
    public download(String url) throws FileNotFoundException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        try {
            URL Url = new URL(url);
            File fp = new File(new Filename().getFilename()+dateFormat +".html");
            OutputStream os = null;          //建立文件输出流
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                os = Files.newOutputStream(fp.toPath());
            }
            URLConnection conn = Url.openConnection();          //打开url连接
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder urlString = new StringBuilder();
            String current;
            while ((current = in.readLine()) != null) {
                urlString.append(current);
            }
            assert os != null;
            os.write(urlString.toString().getBytes());
            os.close();
            Log.d("TEST","文件保存完毕");
            Log.d("TEST", String.valueOf(dateFormat));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}