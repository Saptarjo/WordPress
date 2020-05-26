package com.example.wordpress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WPPostDetails extends AppCompatActivity {
    TextView title;
    WebView webView;
    @Override
    public void  onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        webView=(WebView) findViewById(R.id.postwebview);
        Intent i=getIntent();
        int position=i.getExtras().getInt("itemPosition");

        Log.e("WpPostDetails","title is"+MainActivity.mListPost.get(position).getTitle().getRendered());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(MainActivity.mListPost.get(position).getLink());
        webView.setWebViewClient(new WebViewClient());


    }
}
