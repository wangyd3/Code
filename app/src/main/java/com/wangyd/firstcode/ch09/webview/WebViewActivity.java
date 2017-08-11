package com.wangyd.firstcode.ch09.webview;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.BaseActivity;

public class WebViewActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true); //支持JavaScript
        webView.setWebViewClient(new WebViewClient());//页面跳转的时候不会启动系统浏览器。可以注释验证
        webView.loadUrl("https://www.baidu.com/");
    }
}
