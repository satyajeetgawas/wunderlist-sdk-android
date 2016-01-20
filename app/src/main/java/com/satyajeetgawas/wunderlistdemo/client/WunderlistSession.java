package com.satyajeetgawas.wunderlistdemo.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by satyajeet on 11/20/2015.
 */
public class WunderlistSession {

    public static  String REDIRECT_URI  ;
    public static  String CLIENT_ID  ;
    public static  String CLIENT_SECRET;
    private String mConsumerKey;



    private String mConsumerSecret;
    private String mRedirectUrl;
    public static final String TOKEN_URL = "https://www.wunderlist.com/oauth/access_token";
    private static String token;
    String WUNDERLIST_TOKEN ="WUNDERLIST_TOKEN" ;
    private String WUNDERLIST_SETTINGS = "WUNDERLIST_SETTINGS";
    private static WunderlistSession wunderlistSession;
    private WunderlistLoginResultCallback callbackActivity;
    private WunderlistClient mClient;

    private Context mContext;



    public static WunderlistSession getInstance(){
        return wunderlistSession;
    }

    private WunderlistSession(Context context){
        wunderlistSession = this;
        this.mContext = context;
         mClient= new WunderlistClient();
        token = context.getSharedPreferences(WUNDERLIST_SETTINGS, 0).getString(WUNDERLIST_TOKEN,null);
    }

    public void login(Activity activity)  {
        if(activity instanceof WunderlistLoginResultCallback)
            this.callbackActivity = (WunderlistLoginResultCallback)activity;
        if(isLoggedIn()){
            Toast.makeText(mContext,"Already Logged In",Toast.LENGTH_LONG).show();
           return;
        }

        Intent intent = new Intent(activity,LoginPageActivity.class);
        activity.startActivity(intent);

    }

    public void afterLoginFinished(String accessToken) {
        token = accessToken;
        SharedPreferences settings = mContext.getSharedPreferences(WUNDERLIST_SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(WUNDERLIST_TOKEN, accessToken);
        editor.commit();
        if(callbackActivity!=null){
            callbackActivity.loginResult(token!=null?true:false);
        }

    }

    public WunderlistClient getWunderlistClient(){

        return mClient;
    }


    public boolean isLoggedIn(){
        if (token != null)
            return true;
        else
            return false;
    }






    public String getConsumerSecret() {
        return mConsumerSecret;
    }

    public String getClientId() {
        return mConsumerKey;
    }

    public String getRedirectUrl() {
        return mRedirectUrl;
    }

    public String getToken() {
        return token;
    }

    public Context getApplicationContext() {
        return mContext;
    }


    public static class Builder {
        private final Context mContext;

        public Builder(Context context) {
            mContext = context.getApplicationContext();
        }

        public WunderlistSession build(String consumerKey, String consumerSecret,String redirectUrl) {
            wunderlistSession = new WunderlistSession(mContext);
            CLIENT_ID = consumerKey;
            CLIENT_SECRET = consumerSecret;
            REDIRECT_URI = redirectUrl;
            wunderlistSession.mConsumerKey = consumerKey;
            wunderlistSession.mConsumerSecret = consumerSecret;
            wunderlistSession.mRedirectUrl = redirectUrl;
           return wunderlistSession;
        }
    }

    public void logout(){
        token = null;
        SharedPreferences settings = mContext.getSharedPreferences(WUNDERLIST_SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(WUNDERLIST_TOKEN, null);
        editor.commit();
    }
}







