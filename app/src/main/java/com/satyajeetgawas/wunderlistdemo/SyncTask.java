package com.satyajeetgawas.wunderlistdemo;

import android.os.AsyncTask;
import android.util.Log;

import com.satyajeetgawas.wunderlistdemo.client.WunderlistClient;
import com.satyajeetgawas.wunderlistdemo.client.WunderlistException;
import com.satyajeetgawas.wunderlistdemo.client.WunderlistNote;

import java.util.List;
import java.util.Map;

/**
 * Created by HP M6 on 21-01-2016.
 */

public class SyncTask extends AsyncTask<DemoActivity,Void,Void> {
    private DemoActivity activity;

    @Override
    protected Void doInBackground(DemoActivity... params) {
        activity = params[0];
        Map<String,WunderlistNote> mNotesMap = activity.getNotesMap();
        List<WunderlistNote> listOfNotes;
        List<String> listIds = activity.getListIds();
        WunderlistClient mWunderlistClient = activity.getWunderlistClient();
        try {
            listOfNotes =  mWunderlistClient.getListOfUserNotes();
            for (WunderlistNote note : listOfNotes) {
                listIds.add(note.getId());
                mNotesMap.put(note.getId(), note);
            }
        } catch (WunderlistException exp) {
            Log.e("Wunderlist Client", exp.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void var){
        activity.onTaskFinish();
    }
}
