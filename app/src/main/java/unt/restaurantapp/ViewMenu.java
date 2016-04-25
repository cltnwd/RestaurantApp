package unt.restaurantapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class ViewMenu extends AppCompatActivity {

    ListView menulistview;
    TextView orderTotalView;
    CardView expandableCardView;
    float ordertotal=0;
    ArrayList<MenuItem> currentOrder;

    List<MenuItem> entreelist = new ArrayList<>();
    List<MenuItem> appetizerlist = new ArrayList<>();
    List<MenuItem> drinklist = new ArrayList<>();
    List<MenuItem> dessertlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentOrder = new ArrayList<>();

        orderTotalView = (TextView)findViewById(R.id.orderTotal);
        expandableCardView = (CardView)findViewById(R.id.expandableOrder);

        menulistview = (ListView)findViewById(R.id.menulistview);

        // pull current menu from database
        new GetMenuAsync(this).execute();


        expandableCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableCardView.getMinimumHeight() < 600) {
                    expandableCardView.setMinimumHeight(600);
                }
                else {
                    expandableCardView.setMinimumHeight(56);
                }
            }
        });
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

//        ListAdapter customAdapter1 = new ListAdapter(this, R.layout.itemlistrow, appetizerlist);
//        appetizerlistview.setAdapter(customAdapter1);

        ListAdapter customAdapter2 = new ListAdapter(this, R.layout.itemlistrow, appetizerlist);
        menulistview.setAdapter(customAdapter2);
//
//        ListAdapter customAdapter3 = new ListAdapter(this, R.layout.itemlistrow, dessertlist);
//        dessertlistview.setAdapter(customAdapter3);
//
//        ListAdapter customAdapter4 = new ListAdapter(this, R.layout.itemlistrow, drinklist);
//        drinklistview.setAdapter(customAdapter4);


        menulistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // adds tapped item to your order
                currentOrder.add( entreelist.get(position) );

                // update price
                ordertotal += entreelist.get(position).getPrice();

                // update order total
                orderTotalView.setText("$"+String.format("%.2f", ordertotal));


            }
        });

    }

    public void submitOrder(View view) {

        if (ordertotal == 0) {
            Toast.makeText(getBaseContext(), "Please add items to your order", Toast.LENGTH_SHORT).show();
        }

        else {
            //Toast.makeText(getBaseContext(), "Submitting order...", Toast.LENGTH_SHORT).show();

            // submit the order in background
            new SubmitOrderAsync(this, currentOrder).execute();
        }

    }

    public void orderSubmitted(String jsonString) {

        // create json object from results
        JSONObject jsonroot;
        JSONArray jsonitems = null;
        try {
            jsonroot = new JSONObject(jsonString);

            if (jsonroot.optInt("success") == 1) {
                Toast.makeText(getBaseContext(), "Order submitted!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewMenu.this, CustomerMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            else {
                Toast.makeText(getBaseContext(), "Please try again", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("logcat", "error converting string to json");
        }


    }

    public void updateList(View view) {

        ListAdapter customAdapter;

        if (view.getId() == R.id.appetizerslistbtn) {
            customAdapter = new ListAdapter(this, R.layout.itemlistrow, appetizerlist);
            menulistview.setAdapter(customAdapter);
        }

        else if (view.getId() == R.id.entreeslistbtn) {
            customAdapter = new ListAdapter(this, R.layout.itemlistrow, entreelist);
            menulistview.setAdapter(customAdapter);
        }

        else if (view.getId() == R.id.drinkslistbtn) {
            customAdapter = new ListAdapter(this, R.layout.itemlistrow, drinklist);
            menulistview.setAdapter(customAdapter);
        }

        else if (view.getId() == R.id.dessertslistbtn) {
            customAdapter = new ListAdapter(this, R.layout.itemlistrow, dessertlist);
            menulistview.setAdapter(customAdapter);
        }



    }

}
