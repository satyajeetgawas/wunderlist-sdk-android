package com.satyajeetgawas.wunderlistdemo.client;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.satyajeetgawas.wunderlistdemo.WunderlistDemoApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class LoginWebClient extends WebViewClient {


        private ProgressDialog progress;
        private WebView webView;
        private LoginPageActivity activity;
        public LoginWebClient( LoginPageActivity activity,WebView webView, ProgressDialog progress) {
            super();
            this.progress = progress;
            this.webView = webView;
            this.activity = activity;
            webView.loadUrl("about:blank");
            webView.setVisibility(View.VISIBLE);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            progress.setTitle("Loading");
            progress.setMessage("Loading web page");
            progress.setCancelable(false);
            progress.setIndeterminate(true);
            String url = "https://www.wunderlist.com/oauth/authorize?client_id="
                    + WunderlistSession.getInstance().getClientId()+"&redirect_uri="+ WunderlistSession.getInstance().getRedirectUrl()+"&state=satya";
            webView.loadUrl(url);
        }

        /**
         * Callback executes BEFORE the WebView makes the http request We examine the url to see if it contains the
         * redirect URI, and if so intercept it, hide the webview and display the token. Note - if something goes wrong
         * in the long OAuth2 dance, the user will end up with an error displayed on the webview. They can restart sign
         * in by pushing the [Sign In Again] button.
         *
         * @param view the WebView that executed the callback
         * @param urlStr the URL that the WebView is about to load
         * @return returns true to permit the url to be loaded into the webview
         */
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String urlStr) {

           if (urlStr.startsWith(WunderlistSession.REDIRECT_URI)) {
               activity.startProgress();
                String code = urlStr.substring(urlStr.indexOf("code=") + 5);
                Object[] args = {this,code};
                new GenerateToken().execute(args);
                view.loadUrl("about:blank");
                webView.setVisibility(View.INVISIBLE);
                return true;
            } else {
                  webView.loadUrl(urlStr);
                return true;
            }
        }


        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
            progress.show();
        }


        @Override
        public void onPageFinished(final WebView view, final String url) {
            if(progress!=null)
                progress.dismiss();
        }

    private void stopProgress() {
        activity.stopProgress();
    }

    private class GenerateToken extends AsyncTask<Object,Void,String> {

    private LoginWebClient webClient;
        @Override
        protected String doInBackground(Object... params) {
            try {
                this.webClient = (LoginWebClient)params[0];

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(WunderlistSession.TOKEN_URL);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("client_id", WunderlistSession.CLIENT_ID);
                jsonObject.accumulate("client_secret", WunderlistSession.CLIENT_SECRET);
                jsonObject.accumulate("code", (String)params[1]);

                String json = jsonObject.toString();
                StringEntity se = new StringEntity(json, "UTF-8");
                se.setContentType("application/json");
                httpPost.setEntity(se);
                HttpResponse response = httpClient.execute(httpPost);
                BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                JSONObject jsonObj = new JSONObject(total.toString());
                String token = jsonObj.getString("access_token");
                return token;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String accessToken) {
            webClient.stopProgress();
            activity.finish();
            WunderlistSession.getInstance().afterLoginFinished(accessToken);

        }

    }


}