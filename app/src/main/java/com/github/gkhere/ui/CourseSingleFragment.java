package com.github.gkhere.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gkhere.R;
import com.github.gkhere.adapter.CourseAdapter;
import com.github.gkhere.bean.CourseBean;
import com.github.gkhere.dao.CourseDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/8/17.
 */
public class CourseSingleFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    private RecyclerView rvCourse;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private Context mContext;
    private CourseAdapter adapter;

    public static CourseSingleFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CourseSingleFragment tab = new CourseSingleFragment();
        tab.setArguments(args);
        return tab;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coursesingle, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mContext = getActivity();
        rvCourse = (RecyclerView) view.findViewById(R.id.rv_course);
        //refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.reflash);
        layoutManager = new LinearLayoutManager(getActivity());
        rvCourse.setLayoutManager(layoutManager);

        /*//设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color
                        .holo_orange_light, android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);*/
        searchCourseByDay();
    }

    private void searchCourseByDay() {
        CourseDao courseDao = new CourseDao(mContext);
        List<CourseBean> list = new ArrayList<>();
        switch (mPage) {
            case 1:
                list = courseDao.query("周一");
                break;
            case 2:
                list = courseDao.query("周二");
                break;
            case 3:
                list = courseDao.query("周三");
                break;
            case 4:
                list = courseDao.query("周四");
                break;
            case 5:
                list = courseDao.query("周五");
                break;
        }

        adapter = new CourseAdapter(mContext, list);
        rvCourse.setAdapter(adapter);
    }
}
