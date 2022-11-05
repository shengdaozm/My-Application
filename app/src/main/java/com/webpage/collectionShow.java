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
import com.publicClass.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class collectionShow extends AppCompatActivity {

    SQLiteMaster mSQLiteMaster;
    RecyclerView mRecyclerView;
    public String onUrl;
    List<collection> mCollections = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSQLiteMaster = new SQLiteMaster(collectionShow.this);
        mSQLiteMaster.openDataBase();

        setContentView(R.layout.collectios);
        try {
            getFromDB();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        mRecyclerView= findViewById(R.id.recyclerview2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(collectionShow.this, LinearLayoutManager.VERTICAL, false);//布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MyAdapter());//添加适配器
        mRecyclerView.addItemDecoration(new DividerItemDecoration(collectionShow.this,DividerItemDecoration.VERTICAL));

    }

    class MyAdapter extends RecyclerView.Adapter<collectionShow.MyViewHoder> {
        @NonNull
        @Override
        public collectionShow.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(collectionShow.this, R.layout.collection_item, null);
            return new collectionShow.MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(collectionShow.MyViewHoder holder, int position) {
            collection c = mCollections.get(position);
            holder.mTitle.setText(c.getText()==null?"标题":c.getText());
            holder.mUrl.setText(c.getUrl()==null?"网址":(c.getUrl().substring(0,10)+"..."));
            holder.mlabel.setText(c.getLabel()==null?"默认":c.getLabel());
            holder.mimage.setImageBitmap(c.getWebIcon());
            holder.mRootView.setOnClickListener(view -> {
                onUrl=mCollections.get(position).getUrl();
                //传递参数并跳转
                Intent jump_url=new Intent(collectionShow.this,webpage.class);
                jump_url.putExtra("load_url",onUrl);
                //带参数的活动跳转
                startActivity(jump_url);
            });
        }
        @Override
        public int getItemCount() {return mCollections.size();}
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitle,mUrl,mlabel;
        ImageView mimage;
        ConstraintLayout mRootView;

        public MyViewHoder(final View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.collection_title);
            mUrl = itemView.findViewById(R.id.collection_url);
            mimage=itemView.findViewById(R.id.web_collection_icon);
            mlabel = itemView.findViewById(R.id.collection_label);
            mRootView = itemView.findViewById(R.id.collection_item);
        }
    }

    public void getFromDB() throws IllegalAccessException, InstantiationException {
//        mSQLiteMaster = new SQLiteMaster(collectionShow.this);
//        mSQLiteMaster.openDataBase();
        mCollections = mSQLiteMaster.mCollectionDBDao.queryDataList();
        if(mCollections==null) {
            Log.d("TEST","收藏啥也没有");
        } else {
            Log.d("TEST","收藏存在");
        }
    }

    public void onDestory(){
        mSQLiteMaster.closeDataBase();
    }
}
