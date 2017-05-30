package ayush.abes.timesofindia;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {
private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = (WebView) findViewById(R.id.web_view);

        dialog = new ProgressDialog(this);
        dialog.show();
        dialog.setMessage("Loading...Please Wait");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        // TextView web=(TextView)findViewById(R.id.textView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String NewsUrl = bundle.getString("url");
            String NewsTitle=bundle.getString("title");
            getSupportActionBar().setTitle(NewsTitle);
            webView.getSettings().setJavaScriptEnabled(true);
            ;
            //webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.getSettings().setDomStorageEnabled(true);


            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                    super.onReceivedSslError(view, handler, error);
                }
            });

            if (!NewsUrl.startsWith("http")) {
                webView.loadUrl("http://" + NewsUrl);
                dialog.dismiss();
            } else {
                webView.loadUrl(NewsUrl);
                dialog.dismiss();
            }
            // web.setText(json);

        }

    }
}
