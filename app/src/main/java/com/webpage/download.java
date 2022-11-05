package com.webpage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;


import android.util.Log;
import com.publicClass.Filename;

/**
 * 页面下载功能函数
 */
public class download {
    /**
     * 将html写到文件里面
     * @param html 需要写入的页面的htnl
     */
    public download(String html) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String total_filename=new Filename().getFilename() + String.valueOf(timestamp.getTime() +".html");
        File file=new File(total_filename);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(html);
        writer.flush();
        writer.close();
    }
}