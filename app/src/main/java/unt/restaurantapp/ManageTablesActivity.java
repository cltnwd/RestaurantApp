package unt.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageTablesActivity extends AppCompatActivity {

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";

    List<Table> tables = new ArrayList<>();
    ListView tablelistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tables);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tablelistview = (ListView)findViewById(R.id.tablelistview);

        // create all 10 tables
        for (int i=0; i<10; i++) {
            Table newtable = new Table();
            newtable.setId(i+1);
            newtable.setStatus("OK");

            tables.add(newtable);
        }

        refreshTables();
    }

    public void refreshTables() {
        new ManageTableStatusAsync(this).execute();
    }

    public void parseData(String jsonString) {

        // create json object from results
        JSONObject jsonroot;
        JSONArray jsonitems = null;
        try {
            jsonroot = new JSONObject(jsonString);

            // get the table objects
            jsonitems = jsonroot.optJSONArray("posts");

            // get the status of the table
            String status = null;
            for (int i=0; i<10; i++) {
                status = jsonitems.getJSONObject(i).optString("status");
                tables.get(i).setStatus(status);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("logcat", "error converting string to json");
        }

        setUpListView();
    }

    public void setUpListView() {

        TableListAdapter customAdapter = new TableListAdapter(this, R.layout.tableitemlistrow, tables);
        tablelistview.setAdapter(customAdapter);

        tablelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // pass tableid and status to edit table activity
                Intent intent = new Intent(getBaseContext(), EditTableActivity.class);
                intent.putExtra("tableid", tables.get(position).getId());
                intent.putExtra("status", tables.get(position).getStatus());

                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waitstaff, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        // logout
        if (item.getItemId() == R.id.action_Logout) {
            // TODO: logout for waitstaff
        }

        // refresh table statuses
        if (item.getItemId() == R.id.action_refresh) {
            refreshTables();
        }
        return super.onOptionsItemSelected(item);
    }

}
