package com.z.m3u8.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.z.m3u8.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
    }

    private void initView(){

        WebView webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());

        webView.setWebChromeClient(new WebChromeClient(
        ){


            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("ConsoleMessage","-------------------"+consoleMessage.message()+"::"+consoleMessage.sourceId());

                return super.onConsoleMessage(consoleMessage);
            }
        });
       webView.loadUrl("https://www.baidu.com");


    }
}
