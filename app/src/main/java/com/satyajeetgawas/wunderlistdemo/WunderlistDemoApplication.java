package com.satyajeetgawas.wunderlistdemo;

import android.app.Application;

import com.satyajeetgawas.wunderlistdemo.client.WunderlistSession;

/**
 * Created by HP M6 on 14-01-2016.
 */
public class WunderlistDemoApplication extends Application {
    public static final String CLIENT_ID = "YOUR_CLIENT_ID";
    public static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    public static final String REDIRECT_URI = "http://fintaskanyplace.com";
    @Override
    public void onCreate(){
        super.onCreate();
        //Build the wundelist session
        new WunderlistSession.Builder(this).
                build(CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
    }

}
