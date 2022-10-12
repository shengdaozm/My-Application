package com.webpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.publicClass.history;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史记录展示界面
 */
public class historyShow extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<history> mHistories = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_show);
        Log.d("TEST","进入历史记录页面 !");
        //构造数据
        for(int i=0;i<1000;++i)
            mHistories.add(new history("网址"+(i),"标题"+i));
        mRecyclerView=findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(historyShow.this, LinearLayoutManager.VERTICAL, false);//布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MyAdapter());//添加适配器
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
        }

        @Override
        public int getItemCount() {return mHistories.size();}
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitle,mUrl;

        public MyViewHoder(final View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.history_title);
            mUrl = itemView.findViewById(R.id.history_url);
        }
    }
}
