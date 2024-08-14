package moye.brower.stringbrower;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    protected String default_url = "https://cn.bing.com";

    private WebView webView;

    @Override
    protected void attachBaseContext(Context newBase) {
        Configuration origConfig = newBase.getResources().getConfiguration();
        origConfig.densityDpi = 320;
        Context confBase = newBase.createConfigurationContext(origConfig);
        super.attachBaseContext(confBase);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        default_url = sharedPreferences.getString("default_url","https://www.bing.com");
        editor.putString("default_url",default_url);
        editor.commit();

        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                TextView textView = (TextView) findViewById(R.id.webview_title);
                textView.setText(title);
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Toast.makeText(MainActivity.this,"现在还不支持下载哦", Toast.LENGTH_SHORT).show();
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setInitialScale(50);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                EditText textView = (EditText) findViewById(R.id.webview_url);
                textView.setText(url);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl(default_url);
        LinearLayout layout = (LinearLayout) findViewById(R.id.webview_menu);
        layout.setOnClickListener(view -> layout.setVisibility(View.GONE));
        EditText url_view = (EditText) findViewById(R.id.webview_url);
        url_view.setOnFocusChangeListener((view, b) -> {
            if(b) layout.setVisibility(View.VISIBLE);
            else layout.setVisibility(View.GONE);
        });
        GridLayout cont = (GridLayout) findViewById(R.id.webview_cont);
        cont.setOnClickListener(view -> {
            if(layout.getVisibility() == View.VISIBLE) layout.setVisibility(View.GONE);
            else layout.setVisibility(View.VISIBLE);
        });
        Button reload_btn = (Button) findViewById(R.id.btn_reload);
        reload_btn.setOnClickListener(view -> webView.reload());
        Button back_btn = (Button) findViewById(R.id.btn_back);
        back_btn.setOnClickListener(view -> webView.goBack());
        Button go_btn = (Button) findViewById(R.id.btn_go);
        go_btn.setOnClickListener(view -> {
            webView.loadUrl(url_view.getText().toString());
            url_view.clearFocus();
        });
        Button exit_btn = (Button) findViewById(R.id.btn_exit);
        exit_btn.setOnClickListener(view -> finish());
        Button about_btn = (Button) findViewById(R.id.btn_about);
        about_btn.setOnClickListener(view -> Toast.makeText(this,"当前版本：测试版0.1",Toast.LENGTH_SHORT).show());
    }
}