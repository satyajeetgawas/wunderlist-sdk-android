package com.satyajeetgawas.wunderlistdemo.client;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;


/**
 * Created by satyajeet on 11/21/2015.
 */
public class WunderlistClient {

    RequestQueue mRequestQueue;


    public WunderlistClient(){
        Cache cache = new DiskBasedCache(WunderlistSession.getInstance().getApplicationContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
    }

    public WunderlistUser getUser() throws WunderlistException{

        String url = "https://a.wunderlist.com/api/v1/user";
        String response = getHttpResponse(url);
        WunderlistUser user = null;
        user = new WunderlistUser(response);
        return user;
    }

    public List<WunderlistNote> getListOfUserNotes() throws WunderlistException {
        String url = "http://a.wunderlist.com/api/v1/lists";
        String response = getHttpResponse(url);
        List<WunderlistNote> listNotes = new ArrayList<>();
        try {

            JSONArray jArray = new JSONArray(response);
            for(int i=0;i<jArray.length();i++){
                String id = jArray.getJSONObject(i).getString("id");
                String listUrl = "http://a.wunderlist.com/api/v1/tasks?list_id="+id;
                String listResponse = getHttpResponse(listUrl);
                JSONArray jTasks = new JSONArray(listResponse);
                String contents = "";
                for(int j=0;j<jTasks.length();j++){
                    contents += " "+jTasks.getJSONObject(j).getString("title");
                }

                String title = jArray.getJSONObject(i).getString("title");
                String dateStr = jArray.getJSONObject(i).getString("created_at").substring(0, 10);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(dateStr);
                listNotes.add(new WunderlistNote(id,title,contents,date));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return listNotes;
    }

    private String getHttpResponse(String urlStr) throws WunderlistException {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("X-Access-Token", WunderlistSession.getInstance().getToken());
            urlConnection.setRequestProperty("X-Client-ID", WunderlistSession.getInstance().getClientId());
            return getResponseFromUrlConnection(urlConnection);
        } catch (Exception e) {
            throw new WunderlistException(e.toString());
        }
    }


    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private String getResponseFromUrlConnection(HttpURLConnection urlConnection) throws IOException {
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
