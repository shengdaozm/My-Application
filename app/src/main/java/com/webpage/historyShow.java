package com.webpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    public String Onurl;

    /**
     * 获取历史记录数据
     * @throws IllegalAccessException 访问字段异常
     * @throws InstantiationException 实例化异常
     */
    public void getFromDB() throws IllegalAccessException, InstantiationException {
        mSQLiteMaster = new SQLiteMaster(historyShow.this);
        mSQLiteMaster.openDataBase();
        mHistories = mSQLiteMaster.mHistoryDBDao.queryDataList();
        Collections.reverse(mHistories);//反转mHistories 按照时间的新旧进行排序
    }

    /**
     * 历史界面构造
     * @param savedInstanceState 实例化状态
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST","进入历史记录页面 !");
        setContentView(R.layout.history);
        try {getFromDB();} catch (IllegalAccessException e) {throw new RuntimeException(e);} catch (InstantiationException e) {throw new RuntimeException(e);}
        mRecyclerView= findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(historyShow.this, LinearLayoutManager.VERTICAL, false);//布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MyAdapter());//添加适配器
        mRecyclerView.addItemDecoration(new DividerItemDecoration(historyShow.this,DividerItemDecoration.VERTICAL));
    }

    /**
     * 界面适配器
     */
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
            // 判断当前存入的标题是中文还是英文
            holder.mTitle.setText(h.getText()==null?"标题":h.getText());
            holder.mUrl.setText(h.getUrl()==null?"网址":(h.getUrl().substring(0,10)+"..."));
            holder.mimage.setImageBitmap(h.getWebIcon());
            holder.mRootView.setOnClickListener(view -> {
                Onurl=mHistories.get(position).getUrl();
                //传递参数并跳转
                Intent jump_url=new Intent(historyShow.this,webpage.class);
                jump_url.putExtra("load_url",Onurl);
                //带参数的活动跳转
                startActivity(jump_url);
            });
        }
        @Override
        public int getItemCount() {return mHistories.size();}
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitle,mUrl;
        ImageView mimage;
        ConstraintLayout mRootView;

        public MyViewHoder(final View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.collection_title);
            mUrl = itemView.findViewById(R.id.collection_url);
            mimage=itemView.findViewById(R.id.web_collection_icon);
            mRootView = itemView.findViewById(R.id.history_item);
        }
    }
}
