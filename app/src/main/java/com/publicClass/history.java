package com.publicClass;

public class history {
import android.graphics.Bitmap;

public class history {
    private String url,text;//网址  网页内容标题
    private Bitmap webIcon;//网页的图标

    public history(String url, String text, Bitmap webIcon) {
        this.url = url;
        this.text = text;
        this.webIcon = webIcon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getWebIcon() {
        return webIcon;
    }

    public void setWebIcon(Bitmap webIcon) {
        this.webIcon = webIcon;
    }
}
