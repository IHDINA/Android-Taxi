package com.example.asus.taxiagadirconducteur;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class about extends AppCompatActivity {

    private WebView navigateur=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        navigateur = (WebView) findViewById(R.id.webview);

        navigateur.loadUrl("file:///android_asset/www/site/index.html");
        navigateur.setWebViewClient(new WebViewClient());
        navigateur.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = navigateur.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
