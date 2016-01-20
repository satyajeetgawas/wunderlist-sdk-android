package com.satyajeetgawas.wunderlistdemo.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by satyajeet on 11/21/2015.
 */
public class WunderlistUser {



    private String name;
    private String id;
    private String email;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";



    public WunderlistUser(String jsonResponse){
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            id = jsonObject.getString(ID);
            name = jsonObject.getString(NAME);
            email = jsonObject.getString(EMAIL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
