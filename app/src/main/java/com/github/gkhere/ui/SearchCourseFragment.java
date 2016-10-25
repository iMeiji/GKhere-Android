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
import com.github.gkhere.dao.BaseInfoDao;
import com.github.gkhere.dao.CourseDao;
import com.github.gkhere.utils.PareseCourseUtils;
import com.github.gkhere.utils.TextEncoderUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_hostUrl;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_searchCourseUrl;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_stuId;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_stuName;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_userAgent;

/**
 * Created by Meiji on 2016/8/10.
 */
public class SearchCourseFragment extends Fragment {

    private Context mContext;
    private TabLayout tablayout;
    private ViewPager viewPager;

    private List<CourseBean> courseBeanList;
    private CourseAdapter adapter;


    // 请求数据
    private String searchCourseUrl;
    private String xh;
    private String xm;
    private String Referer;
    private String Host;
    private String UserAgent;

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
        initData();
        searchCourse();
        return view;
    }

    private void initData() {
        BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
        searchCourseUrl = baseInfoDao.query(BASEINFO_searchCourseUrl);
        xh = baseInfoDao.query(BASEINFO_stuId);
        xm = baseInfoDao.query(BASEINFO_stuName);
        Referer = baseInfoDao.query(BASEINFO_searchCourseUrl);
        Host = baseInfoDao.query(BASEINFO_hostUrl);
        UserAgent = baseInfoDao.query(BASEINFO_userAgent);
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
        System.out.println(searchCourseUrl);
        OkHttpUtils.post()
                .url(searchCourseUrl)
                .addParams("xh", xh)
                .addParams("xm", TextEncoderUtils.encoding(xm))
                .addParams("gnmkdm", "N121603")
                // 查询本学期课表 不需要设置以下参数
//                .addParams("__EVENTTARGET", "xnd")
//                .addParams("__EVENTARGUMENT", "")
//                .addParams("__VIEWSTATE", getString(R.string.search_course_viewstate))
//                .addParams("xnd", "2015-2016")
//                .addParams("xqd", "1")
                .addHeader("Referer", Referer)
                .addHeader("Host", Host)
                .addHeader("User-Agent", UserAgent)
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        saveDataToDB(response);
                    }
                });
    }

    private void saveDataToDB(String response) {
        CourseDao courseDao = new CourseDao(mContext);
        //courseDao.deleteAll();
        if (courseDao.queryAll().isEmpty()) {
            Boolean saveSuccess = true;
            courseBeanList = PareseCourseUtils.getCourse(response);
            for (CourseBean bean : courseBeanList) {
                String course = bean.getCourse();
                String day = bean.getDay();
                String timeinfo = bean.getTimeinfo();
                String week = bean.getWeek();
                String teacher = bean.getTeacher();
                String location = bean.getLocation();
                String extra = bean.getExtra();
                boolean isSuccess = courseDao.add(course, day, timeinfo, week,
                        teacher, location, extra);
                if (!isSuccess) {
                    saveSuccess = false;
                    Toast.makeText(mContext, "保存课表失败", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            Toast.makeText(mContext, "保存课表成功", Toast.LENGTH_SHORT).show();
        }
    }
}
