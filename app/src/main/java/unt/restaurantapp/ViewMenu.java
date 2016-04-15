package unt.restaurantapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // pull current menu from database
        new GetMenuAsync(this).execute();
    }

    // parse string into json object
    public void parseData(String jsonString) {

        // create json object from results
        JSONObject jsonroot;
        JSONArray jsonitems = null;
        try {
            jsonroot = new JSONObject(jsonString);

            // move down a level
            //JSONObject jsonphoto = jsonroot.optJSONObject("post");

            // get the photo objects
            jsonitems = jsonroot.optJSONArray("posts");

            // get the names of the items
            String items = " ";
            for (int i=0; i<jsonitems.length(); i++) {
                items += jsonitems.getJSONObject(i).optString("name");
                items += " ";
            }

            // print items
            Toast.makeText(this, items, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("logcat", "error converting string to json");
        }
    }
}
