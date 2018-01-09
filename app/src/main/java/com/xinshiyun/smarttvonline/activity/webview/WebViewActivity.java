package com.xinshiyun.smarttvonline.activity.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.xinshiyun.smarttvonline.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url = getIntent().getStringExtra("url");

        initView();
    }

    private void initView() {
        //webview
        //步骤1. 定义Webview组件
        webView = (WebView) findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8") ;
        webView.setBackgroundColor(0); // 设置背景色
        webView.getBackground().setAlpha(2); // 设置填充透明度 范围：0-255

        //步骤2. 选择加载方式
        //方式1. 加载一个网页：
        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
        }else {
            Toast.makeText(this, "url error", Toast.LENGTH_SHORT).show();
        }

        //步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });




//        webView.loadDataWithBaseURL(null, "加载中。。", "text/html", "utf-8",null);
//        webView.loadDataWithBaseURL(mGetDetail.data.get("hostsUrl"), mGetDetail.data.get("description"), "text/html", "utf-8",null);
//        webView.setVisibility(View.VISIBLE); // 加载完之后进行设置显示，以免加载时初始化效果不好看

    }
}
