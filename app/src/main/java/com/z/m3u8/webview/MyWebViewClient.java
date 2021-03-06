package com.z.m3u8.webview;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.z.m3u8.tool.CallBack;

public class MyWebViewClient extends WebViewClient {


    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        String url=request.getUrl().toString();
      // Log.d("MyWebViewClient","shouldInterceptRequest----:"+url); //20030601
        if(url.endsWith(".m3u8?7")){
            Log.d("MyWebViewClient","-------------------"+url);
            CallBack.get().setUrl(url);
        }
        if(url.endsWith(".m3u8")){
            CallBack.get().setUrl(url);
        }


        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);

      //  Log.d("MyWebViewClient","---------onLoadResource----------"+url);
    }
}
