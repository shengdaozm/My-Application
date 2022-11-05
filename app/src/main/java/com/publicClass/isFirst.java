package com.publicClass;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.IOException;
import com.publicClass.Filename;


/**
 * 判断当前是否是第一次进入软件。
 * 如果是，就不用进行登录和初始界面的换一个界面的演示了
 */
public class isFirst {
    private Boolean isfirst;
    @SuppressLint("SdCardPath")
    private final String filePath=new Filename().getFilename()+"first.txt";

    /**
     * 读取本地的文件，判断是否为第一次使用
     */
    public isFirst() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
            setIsfirst(false);
        } else {
            setIsfirst(true);
        }
    }

    public void setIsfirst(Boolean isfirst) {
        this.isfirst = isfirst;
    }

    public Boolean getIsfirst() {
        return isfirst;
    }
}
