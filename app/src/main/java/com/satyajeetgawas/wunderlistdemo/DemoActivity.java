package com.satyajeetgawas.wunderlistdemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.satyajeetgawas.wunderlistdemo.client.WunderlistClient;
import com.satyajeetgawas.wunderlistdemo.client.WunderlistNote;
import com.satyajeetgawas.wunderlistdemo.client.WunderlistSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoActivity extends ListActivity {
    private List<String> listIds;
    private Map<String,WunderlistNote> mNotesMap;
    private WunderlistClient mWunderlistCLient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.demo_activity);
        listIds = new ArrayList<>();
        mNotesMap = new HashMap<>();
       if(WunderlistSession.getInstance()!=null && WunderlistSession.getInstance().isLoggedIn()) {
            mWunderlistCLient = WunderlistSession.getInstance().getWunderlistClient();
            new SyncTask().execute(this);
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

    public Map<String,WunderlistNote> getNotesMap() {
        return mNotesMap;
    }

    public List<String> getListIds() {
        return listIds;
    }

    public WunderlistClient getWunderlistClient() {
        return mWunderlistCLient;
    }

    public void onTaskFinish() {
        String data = "";
        for(String id:listIds){
            data+="\n"+mNotesMap.get(id).getTitle()+"\n"+mNotesMap.get(id).getContent();
        }
//        TextView txtView = (TextView) findViewById(R.id.textView);
//        txtView.setText(data);
        ArrayList items = new ArrayList<>();
        for(int i=0;i<listIds.size();i++){
            WunderlistNote note = mNotesMap.get(listIds.get(i));
            items.add(Html.fromHtml("<html><strong>" + note.getTitle() + "</strong><br>" + note.getContent() + "</html>"));
        }
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_view,items));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
    }
}
