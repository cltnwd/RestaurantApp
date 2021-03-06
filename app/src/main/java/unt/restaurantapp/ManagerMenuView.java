package unt.restaurantapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManagerMenuView extends AppCompatActivity
{
    ListView menulistview;

    List<MenuItem> entreelist = new ArrayList<>();
    List<MenuItem> appetizerlist = new ArrayList<>();
    List<MenuItem> drinklist = new ArrayList<>();
    List<MenuItem> dessertlist = new ArrayList<>();
    String currentList = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu_view);

        menulistview = (ListView)findViewById(R.id.menulistview);

        // pull current menu from database
        new ManagerGetMenuAsync(this).execute();

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
            //Toast.makeText(this, items, Toast.LENGTH_LONG).show();

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

                // only get entrees
                if (Objects.equals(jsonitems.getJSONObject(i).optString("category"), "entree")) {

                    // get info from json
                    String name = jsonitems.getJSONObject(i).optString("name");
                    String description = jsonitems.getJSONObject(i).optString("descr");
                    int calories = jsonitems.getJSONObject(i).optInt("numcalories");
                    double price = jsonitems.getJSONObject(i).optDouble("price");
                    int itemid = jsonitems.getJSONObject(i).optInt("id");


                    // create menu item
                    MenuItem item = new MenuItem();
                    item.setName(name);
                    item.setDescription(description);
                    item.setCalories(calories);
                    item.setPrice(price);
                    item.setItemid(itemid);

                    // show item if it is available
                    if (jsonitems.getJSONObject(0).optInt("isavailable") == 1) {
                        entreelist.add(item);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        // appetizers
        for (int i=0; i<jsonitems.length(); i++) {
            try {

                // only get appetizers
                if (jsonitems.getJSONObject(i).optString("category").equals("appetizer")) {

                    // get info from json
                    String name = jsonitems.getJSONObject(i).optString("name");
                    String description = jsonitems.getJSONObject(i).optString("description");
                    int calories = jsonitems.getJSONObject(i).optInt("numcalories");
                    double price = jsonitems.getJSONObject(i).optDouble("price");
                    int itemid = jsonitems.getJSONObject(i).optInt("id");


                    // create menu item
                    MenuItem item = new MenuItem();
                    item.setName(name);
                    item.setDescription(description);
                    item.setCalories(calories);
                    item.setPrice(price);
                    item.setItemid(itemid);

                    // show item if it is available
                    if (jsonitems.getJSONObject(0).optInt("isavailable") == 1) {
                        appetizerlist.add(item);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // desserts
        for (int i=0; i<jsonitems.length(); i++) {
            try {

                // only get entrees
                if (jsonitems.getJSONObject(i).optString("category").equals("dessert")) {

                    // get info from json
                    String name = jsonitems.getJSONObject(i).optString("name");
                    String description = jsonitems.getJSONObject(i).optString("description");
                    int calories = jsonitems.getJSONObject(i).optInt("numcalories");
                    double price = jsonitems.getJSONObject(i).optDouble("price");
                    int itemid = jsonitems.getJSONObject(i).optInt("id");


                    // create menu item
                    MenuItem item = new MenuItem();
                    item.setName(name);
                    item.setDescription(description);
                    item.setCalories(calories);
                    item.setPrice(price);
                    item.setItemid(itemid);

                    // show item if it is available
                    if (jsonitems.getJSONObject(0).optInt("isavailable") == 1) {
                        dessertlist.add(item);
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // drinks
        for (int i=0; i<jsonitems.length(); i++) {
            try {

                // only get entrees
                if (jsonitems.getJSONObject(i).optString("category").equals("drink")) {

                    // get info from json
                    String name = jsonitems.getJSONObject(i).optString("name");
                    String description = jsonitems.getJSONObject(i).optString("description");
                    int calories = jsonitems.getJSONObject(i).optInt("numcalories");
                    double price = jsonitems.getJSONObject(i).optDouble("price");
                    int itemid = jsonitems.getJSONObject(i).optInt("id");


                    // create menu item
                    MenuItem item = new MenuItem();
                    item.setName(name);
                    item.setDescription(description);
                    item.setCalories(calories);
                    item.setPrice(price);
                    item.setItemid(itemid);

                    // show item if it is available
                    if (jsonitems.getJSONObject(0).optInt("isavailable") == 1) {
                        drinklist.add(item);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }



    public void updateList(View view) {

        MenuListAdapter customAdapter;

        if (view.getId() == R.id.appetizerslistbtn) {
            customAdapter = new MenuListAdapter(this, R.layout.menuitemlistrow, appetizerlist);
            menulistview.setAdapter(customAdapter);
            currentList = "appetizers";

        }

        else if (view.getId() == R.id.entreeslistbtn) {
            customAdapter = new MenuListAdapter(this, R.layout.menuitemlistrow, entreelist);
            menulistview.setAdapter(customAdapter);
            currentList = "entrees";

        }

        else if (view.getId() == R.id.drinkslistbtn) {
            customAdapter = new MenuListAdapter(this, R.layout.menuitemlistrow, drinklist);
            menulistview.setAdapter(customAdapter);
            currentList = "drinks";

        }

        else if (view.getId() == R.id.dessertslistbtn) {
            customAdapter = new MenuListAdapter(this, R.layout.menuitemlistrow, dessertlist);
            menulistview.setAdapter(customAdapter);
            currentList = "desserts";

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        return true;
    }


}
