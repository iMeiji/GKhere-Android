package com.github.gkhere.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
public class LoginActivity extends AppCompatActivity {

    private CoordinatorLayout layoutRoot;
    private LinearLayout login;
    private EditText etStuId;
    private EditText etStuPasswd;
    private ImageView ivCode;
    private EditText etCode;
    private Button btLogin;

    private String TAG = "LoginActivity : ";
    private Context mContext;
    private String stuId;
    private String stuPasswd;
    private String code;

    private BaseInfoBean baseInfoBean = new BaseInfoBean();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        initView();
        initData();
        initCode();
        initListener();
    }

    /**
     * 读取sp保存的用户名和密码
     */
    private void initData() {
        BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
        stuId = baseInfoDao.query(BASEINFO_stuId);
        stuPasswd = baseInfoDao.query(BASEINFO_stuPasswd);
        etStuId.setText(stuId);
        etStuPasswd.setText(stuPasswd);
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
    private void initCode() {
        OkHttpUtils
                .get()
                .url(HtmlUtils.codeUrl)
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
        HtmlUtils.codeUrl += '?';
        initCode();
    }

    private void initView() {
        layoutRoot = (CoordinatorLayout) findViewById(R.id.layoutRoot);
        login = (LinearLayout) findViewById(R.id.login);
        etStuId = (EditText) findViewById(R.id.et_stuId);
        etStuPasswd = (EditText) findViewById(R.id.et_stuPasswd);
        ivCode = (ImageView) findViewById(R.id.iv_code);
        etCode = (EditText) findViewById(R.id.et_code);
        btLogin = (Button) findViewById(R.id.bt_login);
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
        post.url(HtmlUtils.loginUrl)
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
                        //System.out.println("onResponse" + response);

                        if (response.contains("验证码不正确")) {
                            // 如果源代码包含“验证码不正确”,自动切换验证码
                            HtmlUtils.codeUrl += '?';
                            initCode();
                            Toast.makeText(mContext, "验证码不正确,请重新输入", Toast.LENGTH_SHORT).show();

                        } else if (response.contains("密码错误")) {
                            // 如果源代码包含“密码错误”
                            Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();

                        } else if (response.contains("用户名不存在")) {
                            // 如果源代码包含“用户名不存在”
                            Toast.makeText(mContext, "用户名不存在", Toast.LENGTH_SHORT).show();

                        } else {
                            //登录成功
                            Log.d(TAG, response);
                            Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();

                            // 保存response
                            HtmlUtils.response = response;
                            // 保存用户基本信息
                            saveData();
                            // 跳转
                            startActivity(new Intent(mContext, MainActivity.class));
                        }
                    }
                });
    }

    /**
     * 保存用户基本信息
     * 学号 姓名 个人课表查询 学习成绩查询 登录密码
     */
    private void saveData() {
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
