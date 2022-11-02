package com.publicClass;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;

public class collection {
    //数据库自增ID
    public int id;
    private String url,text;//网址  网页内容标题
    private Bitmap webIcon;//网页的图标
    private String Label;// 用户自定义标签

    /**
     * 无参构造函数
     */
    public collection(){}

    /**
     * collection构造函数
     * @param url 网站的地址
     * @param text 网站的标题
     * @param webIcon 网站的图标
     * @param label 用户自定义标签
     */
    public collection(String url , String text , Bitmap webIcon , String label){
        this.url = url;
        this.text = text;
        this.webIcon = webIcon;
        this.Label = label;
    }

    /**
     * 测试用构造函数
     * @param url 测试网址
     * @param text 测试标题
     */
    public collection(String url, String text) {
        this.url = url;
        this.text = text;
    }

    /**
     * 获得网页的网址
     * @return 网址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置网址
     * @param url 网站网址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获得网站的额标题
     * @return 网站的标题
     */
    public String getText() {
        return text;
    }

    /**
     * 设置网站的文本内容
     * @param text 网站的标题
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获得网站的图标
     * @return 网站的图标
     */
    public Bitmap getWebIcon() {
        return webIcon;
    }

    /**
     * 初始网站的图标
     * @param webIcon 网站的图标
     */
    public void setWebIcon(Bitmap webIcon) {
        this.webIcon = webIcon;
    }


    /**
     * 用户自定义标签
     * @return 用户自定义标签
     */
    public String getLabel() {
        return Label;
    }

    /**
     * 设置收藏网站的用户自定义标签
     * @param label 用户自定义标签
     */
    public void setLabel(String label) {
        Label = label;
    }

}
