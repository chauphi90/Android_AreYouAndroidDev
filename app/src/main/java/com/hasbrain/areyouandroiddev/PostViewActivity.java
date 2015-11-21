package com.hasbrain.areyouandroiddev;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PostViewActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        Bundle data = getIntent().getBundleExtra("data");
        url = data.getString("url");
        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
//        webView.getSettings().setSupportZoom(true);
        webView.setInitialScale(1);// stretch
//        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);// navigation
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setScrollbarFadingEnabled(false);

        webView.setWebViewClient(new PageWebViewClient());
        dialog = ProgressDialog.show(this, "", "Loading ...");
        webView.loadUrl(url);
    }

    class PageWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (dialog != null) {
                dialog.dismiss();
            }
        }

    }
}
