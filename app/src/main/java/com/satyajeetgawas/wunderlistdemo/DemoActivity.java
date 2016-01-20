package com.satyajeetgawas.wunderlistdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.satyajeetgawas.wunderlistdemo.client.WunderlistClient;
import com.satyajeetgawas.wunderlistdemo.client.WunderlistException;
import com.satyajeetgawas.wunderlistdemo.client.WunderlistNote;
import com.satyajeetgawas.wunderlistdemo.client.WunderlistSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoActivity extends AppCompatActivity {
    private List<String> listIds;
    private List<WunderlistNote> listOfNotes;
    private Map<String,WunderlistNote> mNotesMap;
    private WunderlistClient mWunderlistCLient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listIds = new ArrayList<>();
        mNotesMap = new HashMap<>();
        final TextView txtView = (TextView) getLayoutInflater().inflate(R.layout.content_demo,null).findViewById(R.id.text_view);
        if(WunderlistSession.getInstance()!=null && WunderlistSession.getInstance().isLoggedIn()) {
            mWunderlistCLient = WunderlistSession.getInstance().getWunderlistClient();
            Thread tr = new Thread(new Runnable() {
                @Override
                public void run() {
                    String data = "";
                    try {
                        listOfNotes = mWunderlistCLient.getListOfUserNotes();

                        for (WunderlistNote note : listOfNotes) {
                            listIds.add(note.getId());
                            mNotesMap.put(note.getId(), note);
                            data += " " + note;
                        }
                    } catch (WunderlistException exp) {
                        Log.e("Wunderlist Client", exp.getMessage());
                    }
                    txtView.setText(data);
                }

            });
            tr.start();

        }
        else{
            WunderlistSession.getInstance().login(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
