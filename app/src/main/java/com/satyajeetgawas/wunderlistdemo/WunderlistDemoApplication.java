package com.satyajeetgawas.wunderlistdemo;

import android.app.Application;

import com.satyajeetgawas.wunderlistdemo.client.WunderlistSession;

/**
 * Created by HP M6 on 14-01-2016.
 */
public class WunderlistDemoApplication extends Application {
    public static final String CLIENT_ID = "29af49a0f2a719de533d";
    public static final String CLIENT_SECRET = "4a405404731e6cf4ee5c0504eaf96173a148e1220dd3a4046edf78dae414";
    public static final String REDIRECT_URI = "http://fintaskanyplace.com";
    @Override
    public void onCreate(){
        super.onCreate();
        new WunderlistSession.Builder(this).
                build(CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
    }

}
