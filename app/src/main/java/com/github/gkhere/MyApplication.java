package com.github.gkhere;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Meiji on 2016/7/25.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MICROSECONDS)
                .readTimeout(10000L, TimeUnit.MICROSECONDS)
                .cookieJar(cookieJar)
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
