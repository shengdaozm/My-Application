package com.publicClass;

import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.example.myapplication.R;

//TODO 该类还未完成
/**
 * 为方便调试，添加这样的弹窗类，输出打印信息在软件的界面上
 */
public class anim_pop {
    /**
     * 底下弹窗的界面显示
     * @param s 输出的字符串
     * @param view 界面响应
     */
    public void show(String s,View view) {
        //1.构造一个PopupWindow，参数依次是加载的 View，宽高

        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题

        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效

        //设置 popupWindow 显示的位置，参数依次是参照 View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(view, 50, 0);

    }
}
