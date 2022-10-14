package com.webpage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.SQlite.SQLiteMaster;
import com.example.myapplication.R;
import com.publicClass.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 历史记录展示界面
 */
public class historyShow extends AppCompatActivity {

    SQLiteMaster mSQLiteMaster;

    RecyclerView mRecyclerView;
    List<history> mHistories = new ArrayList<>();

    public void getFromDB() throws IllegalAccessException, InstantiationException {
        mSQLiteMaster = new SQLiteMaster(com.webpage.historyShow.this);
        mSQLiteMaster.openDataBase();
        mHistories = mSQLiteMaster.mHistoryDBDao.queryDataList();
        Collections.reverse(mHistories);//反转mHistories 按照时间的新旧进行排序
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_show);
        Log.d("TEST","进入历史记录页面 !");
        //构造数据
        try {
            getFromDB();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        mRecyclerView=findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(historyShow.this, LinearLayoutManager.VERTICAL, false);//布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MyAdapter());//添加适配器
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mSQLiteMaster = new SQLiteMaster(com.webpage.historyShow.this);
        mSQLiteMaster.openDataBase();

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(historyShow.this, R.layout.history_item, null);
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHoder holder, int position) {
            history h = mHistories.get(position);
            holder.mTitle.setText(h.getText());
            holder.mUrl.setText(h.getUrl());
            holder.mimage.setImageBitmap(h.getWebIcon());
        }

        @Override
        public int getItemCount() {return mHistories.size();}
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitle,mUrl;
        ImageView mimage;

        public MyViewHoder(final View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.history_title);
            mUrl = itemView.findViewById(R.id.history_url);
            mimage=itemView.findViewById(R.id.web_history_icon);
        }
    }

    protected void onDestroy() {
        Log.d("TEST","webpage完成销毁！");
        super.onDestroy();
        // 关闭数据库
        mSQLiteMaster.closeDataBase();
    }
}
