package com.webpage;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class history_collections_main extends FragmentActivity  {
    private ImageButton ib1,ib2;//对应历史、收藏的按钮
    private FragmentManager fragmentManager;
    private FragmentTransaction mTransaction;
    private RadioGroup lly;

    private List<Fragment> mFragments=new ArrayList<>();
    private View mFragmeLayout;
    private collectionShow cs;
    private historyShow hs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_collecions_main);
        ib1=findViewById(R.id.histories_imageButton);
        ib2=findViewById(R.id.collections_imageButton);
        mFragmeLayout=findViewById(R.id.frameLayout);
        initView();
        Log.d("TEST","展示页面进入！！！");
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        mTransaction = fragmentManager.beginTransaction();
        lly=findViewById(R.id.whichshow);
        lly.check(R.id.histories_imageButton);
        hs = new historyShow();
        mFragments.add(hs);
        hideOthersFragment(hs, true);
        lly.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId ==R.id.histories_imageButton)
                hideOthersFragment(hs, false);
            else {
                if (cs == null) {
                    cs = new collectionShow();
                    mFragments.add(cs);
                    hideOthersFragment(cs, true);
                } else {hideOthersFragment(cs, false);}
            }
        });
    }

    private void hideOthersFragment(Fragment showFragment, boolean add) {
        mTransaction = fragmentManager.beginTransaction();
        if (add) {mTransaction.add(R.id.frameLayout, showFragment);}

        for (Fragment fragment : mFragments)
            if (showFragment.equals(fragment))
                mTransaction.show(fragment);
            else
                mTransaction.hide(fragment);

        mTransaction.commit();
    }
}
