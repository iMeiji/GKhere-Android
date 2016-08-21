package com.github.gkhere.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.github.gkhere.R;
import com.github.gkhere.utils.HtmlUtils;
import com.github.gkhere.utils.TextEncoderUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Meiji on 2016/8/10.
 */
public class SearchScoreFragment extends Fragment {

    private Spinner spYear;
    private Spinner spSemester;
    private Spinner spMode;
    private ProgressBar progressSearching;
    private ListView listScore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchscore, container,
                false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {

    }

    private void initView(View view) {
        spYear = (Spinner) view.findViewById(R.id.sp_year);
        spSemester = (Spinner) view.findViewById(R.id.sp_Semester);
        spMode = (Spinner) view.findViewById(R.id.sp_mode);
        progressSearching = (ProgressBar) view.findViewById(R.id.progress_searching);
        listScore = (ListView) view.findViewById(R.id.list_score);
    }

    private void searchScore() {
        PostFormBuilder post = OkHttpUtils.post();
        System.out.println(HtmlUtils.searchgradeUrl);
        post.url(HtmlUtils.searchgradeUrl)
                .addParams("xh", HtmlUtils.getStuXh())
                .addParams("xm", TextEncoderUtils.encoding(HtmlUtils.getStuName()))
                .addParams("gnmkdm", "N121605")
                .addParams("__VIEWSTATE", getString(R.string.search_grade_viewstate))
                .addParams("ddlXN", "2014-2015")
                .addParams("ddlXQ", "2")
                .addParams("Button1", "%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF")
                .addHeader("Host", HtmlUtils.hostUrl)
                .addHeader("Referer", HtmlUtils.searchgradeUrl)
                .addHeader("User-Agent", HtmlUtils.userAgent)
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

}
