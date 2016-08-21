package com.github.gkhere.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.gkhere.R;
import com.github.gkhere.adapter.CourseAdapter;
import com.github.gkhere.adapter.CoursePagerAdapter;
import com.github.gkhere.bean.CourseBean;
import com.github.gkhere.dao.CourseDao;
import com.github.gkhere.utils.HtmlUtils;
import com.github.gkhere.utils.PareseCourseUtils;
import com.github.gkhere.utils.TextEncoderUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Meiji on 2016/8/10.
 */
public class SearchCourseFragment extends Fragment {

    private Context mContext;
    private TabLayout tablayout;
    private ViewPager viewPager;

    private List<CourseBean> courseBeanList;
    private CourseAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchcourse, container,
                false);
        initView(view);

        searchCourse();
        return view;
    }

    private void initView(View view) {
        mContext = getActivity();
        courseBeanList = new ArrayList<>();

        // 设置TabLayout和viewpager
        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setAdapter(new CoursePagerAdapter(getFragmentManager(), mContext));
        tablayout.setupWithViewPager(viewPager);
    }

    private void searchCourse() {
        System.out.println(HtmlUtils.searchcourseUrl);
        OkHttpUtils.post().url(HtmlUtils.searchcourseUrl)
                .addParams("xh", HtmlUtils.getStuXh())
                .addParams("xm", TextEncoderUtils.encoding(HtmlUtils.getStuName()))
                .addParams("gnmkdm", "N121603")
                .addParams("__EVENTTARGET", "xnd")
                .addParams("__EVENTARGUMENT", "")
                .addParams("__VIEWSTATE", getString(R.string
                        .search_course_viewstate))
                .addParams("xnd", "2015-2016")
                .addParams("xqd", "1")
                .addHeader("Referer", HtmlUtils.searchcourseUrl)
                .addHeader("Host", HtmlUtils.hostUrl)
                .addHeader("User-Agent", HtmlUtils.userAgent)
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        courseBeanList = PareseCourseUtils.getCourse(response);
                        saveDataToDB();
                    }
                });
    }

    private void saveDataToDB() {
        CourseDao courseDao = new CourseDao(mContext);
        courseDao.deleteAll();
        Boolean saveSuccess = true;
        for (CourseBean bean : courseBeanList) {
            String name = bean.getCourseName();
            String time = bean.getCourseTime();
            String timeDetail = bean.getCourseTimeDetail();
            String teacher = bean.getCourseTeacher();
            String location = bean.getCourseLocation();
            String info = bean.getCourseInfo();
            boolean isSuccess = courseDao.add(name, time, timeDetail, teacher,
                    location,info);
            if (!isSuccess) {
                saveSuccess = false;
                Toast.makeText(mContext, "保存课表失败", Toast.LENGTH_SHORT)
                        .show();
                break;
            }
        }
        Toast.makeText(mContext, "保存课表成功", Toast.LENGTH_SHORT).show();
    }
}
