package unt.restaurantapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class EditMenu extends AppCompatActivity {

    ArrayList<MenuItem> menu = new ArrayList<>();

    ListView editmenulv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editmenulv = (ListView)findViewById(R.id.editmenulv);

        // get the menu items
        new GetMenuKitchenAsync(this).execute();
    }

    public void parseData(String jsonString) {

        // create json object from results
        JSONObject jsonroot;
        JSONArray jsonitems = null;
        try {
            jsonroot = new JSONObject(jsonString);

            // get the photo objects
            jsonitems = jsonroot.optJSONArray("posts");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("logcat", "error converting string to json");
        }

        setUpListView(jsonitems);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setUpListView(JSONArray jsonitems) {

        if (jsonitems == null) {
            return;
        }

        // entrees
        for (int i=0; i<jsonitems.length(); i++) {

            try {

                // get info from json
                String name = jsonitems.getJSONObject(i).optString("name");
                String description = jsonitems.getJSONObject(i).optString("descr");
                int calories = jsonitems.getJSONObject(i).optInt("numcalories");
                double price = jsonitems.getJSONObject(i).optDouble("price");
                int itemid = jsonitems.getJSONObject(i).optInt("id");
                int available = jsonitems.getJSONObject(i).getInt("isavailable");

                boolean isavailable;
                if (available == 0) {
                    isavailable = false;
                }
                else {
                    isavailable = true;
                }

                // create menu item
                MenuItem item = new MenuItem();
                item.setName(name);
                item.setDescription(description);
                item.setCalories(calories);
                item.setPrice(price);
                item.setItemid(itemid);
                item.setAvailable(isavailable);

                menu.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final EditMenuListAdapter customAdapter = new EditMenuListAdapter(this, R.layout.editmenuitemlistrow, menu);
        editmenulv.setAdapter(customAdapter);

        editmenulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (menu.get(position).isAvailable()) {
                    menu.get(position).setAvailable(false);
                    customAdapter.notifyDataSetChanged();
                }
                else {
                    menu.get(position).setAvailable(true);
                    customAdapter.notifyDataSetChanged();
                }

                new UpdateMenuAsync(menu.get(position).getItemid(), menu.get(position).isAvailable()).execute();
            }
        });
    }
}
