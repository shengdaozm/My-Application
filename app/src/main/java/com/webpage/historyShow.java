package com.webpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
public class historyShow extends Fragment {

    SQLiteMaster mSQLiteMaster;

    RecyclerView mRecyclerView;
    List<history> mHistories = new ArrayList<>();

    public void getFromDB() throws IllegalAccessException, InstantiationException {
        mSQLiteMaster = new SQLiteMaster(getActivity());
        mSQLiteMaster.openDataBase();
        mHistories = mSQLiteMaster.mHistoryDBDao.queryDataList();
        Collections.reverse(mHistories);//反转mHistories 按照时间的新旧进行排序
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TEST","进入历史记录页面 !");
        View view=inflater.inflate(R.layout.history, container, false);
        //获得数据
        try {getFromDB();} catch (IllegalAccessException e) {throw new RuntimeException(e);} catch (InstantiationException e) {throw new RuntimeException(e);}

        mRecyclerView= view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);//布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MyAdapter());//添加适配器
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getContext(), R.layout.history_item, null);
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHoder holder, int position) {
            history h = mHistories.get(position);
            holder.mTitle.setText(h.getText()==null?"标题":h.getText());
            holder.mUrl.setText(h.getUrl()==null?"网址":(h.getUrl().substring(0,10)+"..."));
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
}
