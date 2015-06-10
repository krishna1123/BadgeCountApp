package com.example.krishna.badgecountapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.krishna.badgecountapp.R;

/**
 * Created by krishna on 20/5/15.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        WebView webView = (WebView) findViewById(R.id.wv_html);
    }
}
