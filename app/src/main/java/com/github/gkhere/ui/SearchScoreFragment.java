package com.github.gkhere.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gkhere.R;
import com.github.gkhere.adapter.ScoreAdapter;
import com.github.gkhere.bean.ScoreBean;
import com.github.gkhere.utils.HtmlUtils;
import com.github.gkhere.utils.ParseScoreUtils;
import com.github.gkhere.utils.TextEncoderUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Meiji on 2016/8/10.
 */
public class SearchScoreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private TextView tvContent;
    private Spinner spYear;
    private Spinner spSemester;
    private Spinner spMode;
    private ProgressBar progressBar;
    private ListView listScore;
    private Context mContext;

    // 下拉框(学年 学期 查询方式)及适配器
    private List<String> yearList = new ArrayList<>();
    private List<String> semesterList = new ArrayList<>();
    private List<String> modeList = new ArrayList<>();
    private ArrayAdapter<String> yearAdapter;
    private ArrayAdapter<String> semesterAdapter;
    private ArrayAdapter<String> modeAdapter;

    // 请求数据
    private String ddlXN = "";
    private String ddlXQ = "";
    private String selectMode = "";
    private String requestButton = "";
    private String VIEWSTATE = "";
    private static String BUTTON_Semester = "%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF";
    private static String BUTTON_Year = "%B0%B4%D1%A7%C4%EA%B2%E9%D1%AF";
    private static String BUTTON_Inschool = "%D4%DA%D0%A3%D1%A7%CF%B0%B3%C9%BC%A8%B2%E9%D1%AF";

    private List<ScoreBean> scoreBeanList;
    private String mResponse;

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
        initYearList();

//        initListener();
        return view;
    }

    private void initSpinner() {
        yearAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
                yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);
        spYear.setSelection(0);

        semesterAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
                semesterList);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSemester.setAdapter(semesterAdapter);
        spSemester.setSelection(0);

        modeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
                modeList);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMode.setAdapter(modeAdapter);
        spMode.setSelection(0);

        spYear.setOnItemSelectedListener(this);
        spSemester.setOnItemSelectedListener(this);
        spMode.setOnItemSelectedListener(this);
    }

    private void initData() {
        yearList = ParseScoreUtils.parseSelectYearList(mResponse);
        semesterList.add("1");
        semesterList.add("2");
        semesterList.add("3");
        modeList.add("学期成绩");
        modeList.add("学年成绩");
        modeList.add("在校成绩");

        initSpinner();
    }

    private void initYearList() {

        OkHttpUtils.get()
                .url(HtmlUtils.searchscoreUrl)
                .addHeader("Host", HtmlUtils.hostUrl)
                .addHeader("Referer", HtmlUtils.searchscoreUrl)
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(mContext, "数据初始化失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mResponse = response;
                        System.out.println("response" + response);
                        initData();
                    }
                });
    }

    private void initView(View view) {
        //tvContent = (TextView) view.findViewById(R.id.tv_content);
        spYear = (Spinner) view.findViewById(R.id.sp_year);
        spSemester = (Spinner) view.findViewById(R.id.sp_Semester);
        spMode = (Spinner) view.findViewById(R.id.sp_mode);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_searching);
        listScore = (ListView) view.findViewById(R.id.list_score);
        mContext = getActivity();
    }

    private void searchScore() {
        PostFormBuilder post = OkHttpUtils.post();
        System.out.println(HtmlUtils.searchscoreUrl);
        if (ddlXN.equals("") || ddlXQ.equals("") || requestButton.equals("") || selectMode.equals("")) {
            return;
        }
        post.url(HtmlUtils.searchscoreUrl)
                .addParams("xh", HtmlUtils.getStuXh())
                .addParams("xm", TextEncoderUtils.encoding(HtmlUtils.getStuName()))
                .addParams("gnmkdm", "N121605")
                .addParams("__VIEWSTATE", VIEWSTATE)
                .addParams("ddlXN", ddlXN)
                .addParams("ddlXQ", ddlXQ)
                .addParams(requestButton, selectMode)
                .addHeader("Host", HtmlUtils.hostUrl)
                .addHeader("Referer", HtmlUtils.searchscoreUrl)
                .addHeader("User-Agent", HtmlUtils.userAgent)
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "暂时没有成绩", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //tvContent.setText(response);
                        ParseScoreUtils utils = new ParseScoreUtils(response);
                        scoreBeanList =  utils.parseScore();
                        ScoreAdapter adapter = new ScoreAdapter(scoreBeanList, mContext);
                        listScore.setAdapter(adapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {

            case R.id.sp_year:
                ddlXN = yearList.get(i);
                break;
            case R.id.sp_Semester:
                ddlXQ = semesterList.get(i);
                break;
            case R.id.sp_mode:
                String mode = modeList.get(i);
                switch (mode) {
                    case "学期成绩":
                        requestButton = "Button1";
                        selectMode = BUTTON_Year;
                        VIEWSTATE = getResources().getString(R.string.search_grade_viewstate_by_year);
                        break;
                    case "学年成绩":
                        requestButton = "Button5";
                        selectMode = BUTTON_Semester;
                        VIEWSTATE = getResources().getString(R.string.search_grade_viewstate_by_semester);
                        break;
                    case "在校成绩":
                        requestButton = "Button2";
                        selectMode = BUTTON_Inschool;
                        VIEWSTATE = getResources().getString(R.string.search_grade_viewstate_by_inschool);
                        break;
                }
        }
        progressBar.setVisibility(View.VISIBLE);
        searchScore();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
