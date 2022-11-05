package com.publicClass;

import android.annotation.SuppressLint;

/**
 * 文件路径统一构造
 */
public class Filename {

    /**
     * 获得文件路径名
     * @return 文件路径名
     */
    @SuppressLint("SdCardPath")
    public String getFilename() {
        return "/data/data/com.example.myapplication/";
    }
}
