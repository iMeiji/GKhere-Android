package com.github.gkhere.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.gkhere.R;
import com.github.gkhere.bean.BaseInfoBean;
import com.github.gkhere.dao.BaseInfoDao;
import com.github.gkhere.utils.HtmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_codeUrl;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_hostUrl;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_loginUrl;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_searchCourseUrl;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_searchScoreUrl;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_stuId;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_stuName;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_stuPasswd;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_userAgent;

/**
 * Created by Meiji on 2016/8/10.
 */
public class LoginFragment extends Fragment {

    private CoordinatorLayout layoutRoot;
    private LinearLayout login;
    private EditText etStuId;
    private EditText etStuPasswd;
    private ImageView ivCode;
    private EditText etCode;
    private Button btLogin;

    private Context mContext;
    private String stuId;
    private String stuPasswd;
    private String code;

    private BaseInfoBean baseInfoBean = new BaseInfoBean();
    private boolean isFirstTime;
    private long exitTime;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        initData();
        initCode(baseInfoBean.getCodeUrl());
        initListener();
        return view;
    }

    /**
     * 读取已保存的用户名和密码
     */
    private void initData() {
        BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
        stuId = baseInfoDao.query(BASEINFO_stuId);
        stuPasswd = baseInfoDao.query(BASEINFO_stuPasswd);
        etStuId.setText(stuId);
        etStuPasswd.setText(stuPasswd);
        if (TextUtils.isEmpty(stuId)) {
            isFirstTime = true;
        }
    }

    private void initListener() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLogin();
            }
        });
    }


    /**
     * 获取验证码
     */
    private void initCode(String codeUrl) {
        OkHttpUtils
                .get()
                .url(codeUrl)
                .tag(this)
                .build()
                .connTimeOut(5000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        // 图片显示"网络不给力"
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        ivCode.setImageBitmap(response);
                    }
                });
    }

    /**
     * 刷新验证码
     *
     * @param view
     */
    public void requestCode(View view) {
        initCode(baseInfoBean.getCodeUrl() + "?");
    }

    private void initView(View view) {
        layoutRoot = (CoordinatorLayout) view.findViewById(R.id.layoutRoot);
        login = (LinearLayout) view.findViewById(R.id.login);
        etStuId = (EditText) view.findViewById(R.id.et_stuId);
        etStuPasswd = (EditText) view.findViewById(R.id.et_stuPasswd);
        ivCode = (ImageView) view.findViewById(R.id.iv_code);
        etCode = (EditText) view.findViewById(R.id.et_code);
        btLogin = (Button) view.findViewById(R.id.bt_login);
        mContext = getActivity();
    }

    private void requestLogin() {

        // 用户输入的值
        stuId = etStuId.getText().toString();
        stuPasswd = etStuPasswd.getText().toString();
        code = etCode.getText().toString();

        // 这里应该做一些空值判断
        if (TextUtils.isEmpty(stuId) || TextUtils.isEmpty(stuPasswd) || TextUtils.isEmpty(code)) {
            Toast.makeText(mContext, "请输入完整", Toast.LENGTH_SHORT).show();
            return;
        }

        // 请求登录
        PostFormBuilder post = OkHttpUtils.post();
        post.url(baseInfoBean.getLoginUrl())
                // 下面数据抓包可以得到
                .addParams("__VIEWSTATE",
                        "dDw3OTkxMjIwNTU7Oz5vJ/yYUi9dD4fEnRUKesDFl8hEKA==")
                .addParams("TextBox1", stuId)   // 学号
                .addParams("TextBox2", stuPasswd)  // 密码
                .addParams("TextBox3", code)    // 验证码
                .addParams("RadioButtonList1", "%D1%A7%C9%FA")
                .addParams("Button1", "")
                .addHeader("Referer", baseInfoBean.getLoginUrl())
                .addHeader("Host", baseInfoBean.getHostUrl())
                .addHeader("User-Agent", baseInfoBean.getUserAgent())
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "你输入的信息有误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        // 请求成功，response就是得到的html文件（网页源代码）
                        View focusView = null;
                        if (response.contains("验证码不正确")) {
                            etCode.setError("验证码不正确");
                            focusView = etCode;
                        } else if (response.contains("密码错误")) {
                            etStuPasswd.setError("密码不正确");
                            focusView = etStuPasswd;
                        } else if (response.contains("用户名不存在")) {
                            etStuId.setError("用户名不正确");
                            focusView = etStuId;
                        }
                        if (focusView != null) {
                            // 自动聚焦到输入框
                            focusView.requestFocus();
                            // 自动切换验证码
                            initCode(baseInfoBean.getCodeUrl() + "?");
                        } else {
                            // 登录成功 判断是否第一次登录
                            if (isFirstTime) {
                                // 保存response
                                HtmlUtils.response = response;
                                showSaveDataDialog();
                            }
                            // 跳转
                            startActivity(new Intent(mContext, MainActivity.class));
                        }
                    }
                });
    }

    /**
     * 显示弹窗 保存数据中
     */
    private void showSaveDataDialog() {
        ProgressDialog waitDialog = new ProgressDialog(mContext);
        waitDialog.setTitle("请稍后");
        waitDialog.setMessage("Loading...");
        waitDialog.setCanceledOnTouchOutside(false);
        waitDialog.show();
        saveDataToDB();
        waitDialog.dismiss();
    }

    /**
     * 保存用户基本信息
     * 学号 姓名 个人课表查询 学习成绩查询 登录密码
     */
    private void saveDataToDB() {
        BaseInfoBean bean = HtmlUtils.encoder(baseInfoBean);
        BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
        baseInfoDao.add(BASEINFO_stuId, bean.getStuId());
        baseInfoDao.add(BASEINFO_stuName, bean.getStuName());
        baseInfoDao.add(BASEINFO_stuPasswd, stuPasswd);
        baseInfoDao.add(BASEINFO_hostUrl, bean.getHostUrl());
        baseInfoDao.add(BASEINFO_codeUrl, bean.getCodeUrl());
        baseInfoDao.add(BASEINFO_loginUrl, bean.getLoginUrl());
        baseInfoDao.add(BASEINFO_userAgent, bean.getUserAgent());
        baseInfoDao.add(BASEINFO_searchScoreUrl, bean.getSearchScoreUrl());
        baseInfoDao.add(BASEINFO_searchCourseUrl, bean.getSearchCourseUrl());
    }

}
