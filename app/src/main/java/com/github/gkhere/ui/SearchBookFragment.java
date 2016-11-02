package com.github.gkhere.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.gkhere.R;

/**
 * Created by Meiji on 2016/11/1.
 */

public class SearchBookFragment extends Fragment {

    private String url = "http://61.142.33.201:8090/sms/opac/search/showiphoneSearch.action";
    private WebView mWebView;
    private WebViewClient mWebViewClient;
    private View.OnKeyListener mOnKeyListener;
    private WebChromeClient mWebChromeClient;
    private ProgressBar progress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchbook, container, false);

        initData();
        initView(view);
        return view;
    }

    private void initData() {
        mWebViewClient = new WebViewClient() {
            // 在网页上的所有加载都经过这个方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

        };

        mOnKeyListener = new View.OnKeyListener() {
            // 实现返回按钮返回上一页
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        };

        mWebChromeClient = new WebChromeClient() {
            // 显示进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                if (newProgress >= 100) {
                    progress.setVisibility(View.GONE);
                } else {
                    if (progress.getVisibility() == View.GONE) {
                        progress.setVisibility(View.VISIBLE);
                        progress.setProgress(newProgress);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        };
    }

    private void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.webView);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAppCacheEnabled(true);

        mWebView.requestFocus();
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setOnKeyListener(mOnKeyListener);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    @Override
    public void onDestroy() {

        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, null, "text/html", "UTF-8", null);
            mWebView.clearHistory();
            mWebView.clearCache(true);
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
