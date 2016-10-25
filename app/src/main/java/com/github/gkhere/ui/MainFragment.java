package com.github.gkhere.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gkhere.R;

public class MainFragment extends Fragment {

    private RecyclerView rv_courseList;
    private TextView tv_nullCourse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rv_courseList = (RecyclerView) view.findViewById(R.id.course_list);
        tv_nullCourse = (TextView) view.findViewById(R.id.tv_null_course);
    }


}
