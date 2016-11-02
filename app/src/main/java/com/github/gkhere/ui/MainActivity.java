package com.github.gkhere.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.gkhere.R;
import com.github.gkhere.dao.BaseInfoDao;
import com.github.gkhere.dao.CourseDao;

import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_stuId;
import static com.github.gkhere.bean.BaseInfoBean.BASEINFO_stuName;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext = this;
    private CourseDao courseDao = new CourseDao(mContext);
    private BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
    private TextView nav_header_stuName;
    private TextView nav_header_stuId;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        // 默认打开MainFragment
        replaceFragment(new MainFragment(), "主页");
    }

    /**
     * 设置侧栏头部信息
     */
    private void initData() {
        String stuName = baseInfoDao.query(BASEINFO_stuName);
        String stuId = baseInfoDao.query(BASEINFO_stuId);
        System.out.println(stuName + stuId);
        if (!TextUtils.isEmpty(stuName) && !TextUtils.isEmpty(stuId)) {
            nav_header_stuName.setText(stuName);
            nav_header_stuId.setText(stuId);
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // 获取侧栏的headerView
        View headerView = navigationView.getHeaderView(0);
        nav_header_stuName = (TextView) headerView.findViewById(R.id.nav_header_stuName);
        nav_header_stuId = (TextView) headerView.findViewById(R.id.nav_header_stuId);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if ((currentTime - exitTime) < 2000) {
            super.onBackPressed();
        } else {
            Snackbar.make(getCurrentFocus(), "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = currentTime;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.main:
                replaceFragment(new MainFragment(), "主页");
                break;

            case R.id.search_score:
                replaceFragment(new SearchScoreFragment(), "查成绩");
                break;

            case R.id.search_course:
                replaceFragment(new SearchCourseFragment(), "查课表");
                break;

            case R.id.search_book:
                replaceFragment(new SearchBookFragment(), "图书馆");
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_logout:
                //注销
                showLogoutDialog();
                break;
        }

        return true;
    }

    // 注销登录弹窗
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("注销登录");
        builder.setMessage("注销登录会清除已保存的课表数据...");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 清除所有用户数据
                courseDao.deleteAll();
                baseInfoDao.deleteAll();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Navigation header
     * 点击头像登录
     *
     * @param view
     */
    public void startLogin(View view) {
        replaceFragment(new LoginFragment(), "登录");
    }

    private void replaceFragment(Fragment fragment, String toolbarTitle) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                fragment).commit();
        toolbar.setTitle(toolbarTitle);
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
