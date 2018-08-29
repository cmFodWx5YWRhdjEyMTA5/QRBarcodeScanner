package com.example.framgiadoanmanhtuan.myandroidqrbarcodescanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private final String URL = "URL";
    private WebView mWebView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webview);
        mWebView = findViewById(R.id.web_view);
        Intent intent = getIntent();
        if (intent != null) {
            String query = intent.getStringExtra(URL);
            String url = "https://www.google.com/search?q=" + query;
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setInitialScale(1);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setDisplayZoomControls(true);
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.clearCache(true);
            mWebView.setBackgroundColor(Color.TRANSPARENT);
            mWebView.loadUrl(url);
        }
        //Navigate back press
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (mWebView.canGoBack()) {
                                mWebView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });
    }
}
