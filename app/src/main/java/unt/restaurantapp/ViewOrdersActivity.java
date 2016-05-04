package unt.restaurantapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ViewOrdersActivity extends AppCompatActivity {

    ArrayList<Orders> orders = new ArrayList<>();
    ListView orderlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderlistview = (ListView)findViewById(R.id.liveOrderList);

        refreshOrders();
    }

    public void refreshOrders() {
        orders.clear();
        new GetOrdersAsync(this).execute();
    }

    public void parseData(String jsonString) {

        // create json object from results
        JSONObject jsonroot;
        JSONArray jsonitems = null;
        try {
            jsonroot = new JSONObject(jsonString);

            // move down a level
            //JSONObject jsonphoto = jsonroot.optJSONObject("post");
            if (jsonroot.optInt("success") == 1) {
                // get the photo objects
                jsonitems = jsonroot.optJSONArray("posts");

                setUpListView(jsonitems);
            }
            else {
                Toast.makeText(ViewOrdersActivity.this, "No orders available", Toast.LENGTH_SHORT).show();
            }
            // print items
            //Toast.makeText(this, items, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("logcat", "error converting string to json");
        }


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setUpListView(JSONArray jsonitems) {

        if (jsonitems == null) {
            Toast.makeText(this, "Something went wrong with json parsing", Toast.LENGTH_SHORT).show();
        }

        // entrees
        for (int i=0; i<jsonitems.length(); i++) {

            try {

                // get info from json
                String orderstring = jsonitems.getJSONObject(i).optString("orderstring");
                String status = jsonitems.getJSONObject(i).optString("status");
                int tableid = jsonitems.getJSONObject(i).optInt("tableid");
                int realid = jsonitems.getJSONObject(i).optInt("realid");

                // create order item
                Orders order = new Orders();
                order.setOrderstring(orderstring);
                order.setStatus(status);
                order.setTableid(tableid);
                order.setRealid(realid);

                orders.add(order);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        System.out.println(orders.get(0).getTableid());

        if (orders != null) {
            OrderListAdapter customAdapter = new OrderListAdapter(this, R.layout.orderitemlistrow, orders);
            orderlistview.setAdapter(customAdapter);


            orderlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), ViewOrderActivity.class);
                    intent.putExtra("realid", orders.get(position).getRealid());
                    intent.putExtra("tableid", orders.get(position).getTableid());
                    intent.putExtra("status", orders.get(position).getStatus());
                    intent.putExtra("orderstring", orders.get(position).getOrderstring());
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this, "Order list fail", Toast.LENGTH_SHORT).show();
        }
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
            // TODO: logout for kitchen staff
        }

        // refresh table statuses
        if (item.getItemId() == R.id.action_refresh) {
            refreshOrders();
        }
        return super.onOptionsItemSelected(item);
    }

}
