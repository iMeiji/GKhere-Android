package com.github.gkhere.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gkhere.R;
import com.github.gkhere.dao.CourseDao;

public class MainFragment extends Fragment {

    private RecyclerView rv_courseList;
    private TextView tv_nullCourse;

    private CourseDao courseDao;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        courseDao = new CourseDao(mContext);
    }


    private void initView(View view) {
        mContext = getActivity();
        rv_courseList = (RecyclerView) view.findViewById(R.id.rv_courseList);
        tv_nullCourse = (TextView) view.findViewById(R.id.tv_nullCourse);
    }
}
