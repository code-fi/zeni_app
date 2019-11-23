package org.zeniafrik.ui.market;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import org.zeniafrik.R;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.ui.extras.ActivityIndicatorDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.error_view)
    LinearLayout error_view;
    private Unbinder unbinder;
    private ActivityIndicatorDialog mLoad;

    @OnClick(R.id.retry_btn)
    public void reloadWebView() {
        error_view.setVisibility(View.GONE);
        mLoad.show();
        webView.reload();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoad = new ActivityIndicatorDialog(WebViewActivity.this);

        setContentView(R.layout.activity_web_view);
        unbinder = ButterKnife.bind(WebViewActivity.this);

        String[] data = getIntent().getExtras().getStringArray("a");
        toolbar.setTitle(data[0]);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.setWebViewClient(new ZeniAfrikWebClient());
        webView.loadUrl(data[1]);
    }

    @Override
    public boolean onSupportNavigateUp() {
        webView.stopLoading();
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        mLoad.dismiss();
        unbinder.unbind();
        super.onDestroy();
    }

    class ZeniAfrikWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!mLoad.isShowing()) mLoad.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.setVisibility(View.VISIBLE);
            mLoad.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            error_view.setVisibility(View.VISIBLE);
            mLoad.hide();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getHost().equals("zeniconsult.com")) return false;
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
    }
}
