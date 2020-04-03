package com.z.m3u8.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.z.m3u8.R;
import com.z.m3u8.tool.CallBack;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
    }

    WebView webView;

    private void initView() {

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());

        webView.setWebChromeClient(new WebChromeClient(
        ) {


            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("ConsoleMessage", "-------------------" + consoleMessage.message() + "::" + consoleMessage.sourceId());

                return super.onConsoleMessage(consoleMessage);
            }
        });
      webView.loadUrl("https://www.baidu.com");

        findViewById(R.id.btnDown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallBack.get().callUrl();
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
    }
}
