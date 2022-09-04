package com.firstpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;

public class firstpage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firstpage, container, false);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        assert getArguments() != null;
        String text = getArguments().getString("text");
        tv_content.setText(text);
        return view;
    }
}
