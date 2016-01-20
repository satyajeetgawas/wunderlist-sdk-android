package com.satyajeetgawas.wunderlistdemo.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;


/**
 * Created by satyajeet on 11/20/2015.
 */
public class LoginPageActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout activityLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 16);

        WebView webView = new WebView(this);
        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(tlp);
        activityLayout.addView(webView);
        setContentView(activityLayout);

        ProgressDialog progressDialog = new ProgressDialog(this);
        webView.setWebViewClient(new LoginWebClient(this,webView, progressDialog));
    }

    public void startProgress(){
        progressDialog = ProgressDialog.show(this,"Loading","Logging in",true);

    }

    public void stopProgress() {
        progressDialog.dismiss();
    }
}
