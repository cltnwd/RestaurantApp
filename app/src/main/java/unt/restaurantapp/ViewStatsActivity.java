package unt.restaurantapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewStatsActivity extends AppCompatActivity {

    ArrayList<Orders> orders = new ArrayList<>();
    float dailyRevenue = 0;
    TextView rev, top3tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rev = (TextView)findViewById(R.id.rev);
        top3tv = (TextView)findViewById(R.id.top3);
        getStats();
    }

    public void getStats() {
        new GetStatsAsync(this).execute();
    }

    public void parseData(String jsonString) {

        // create json object from results
        JSONObject jsonroot = null;
        JSONArray jsonitems = null;
        try {
            jsonroot = new JSONObject(jsonString);
            jsonitems = jsonroot.getJSONArray("posts");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (jsonroot.optInt("success") == 1) {

            // get daily revenue
            for (int i=0; i<jsonitems.length(); i++) {
                try {
                    dailyRevenue += jsonitems.getJSONObject(i).optDouble("price");
                } catch (JSONException e) {
                    e.printStackTrace();
                    dailyRevenue += 0;
                }
            }

            System.out.println("dailyrev:: "+ dailyRevenue);
            rev.setText("Daily Revenue: $"+String.format("%.2f",dailyRevenue));

            String orders = "";
            // build string of all orders
            for (int i=0; i<jsonitems.length(); i++) {
                try {
                    orders += jsonitems.getJSONObject(i).optString("orderstring");
                    orders += " ";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(orders);

            ArrayList<FreqWord> itemscnt = new ArrayList<>();

            int items[] = new int[41];
            for (int i=0; i<41; i++) {
                items[i] = 0;
            }

            // split orders into items
            String[] arr = orders.split(" ");
            for ( String ss : arr) {
                int i = Integer.valueOf(ss);
                items[i]++;

                //System.out.println(ss);
            }
            int top1=0, top2=0, top3=0;

            int max1 = items[0];
            // find top sold items
            for (int i=0; i<41; i++) {
                if (items[i] > max1) {
                    max1 = items[i];
                    top1 = i;
                }
            }
            items[top1] = 0;

            // find second top sold items
            int max2 = items[0];
            for (int i=0; i<41; i++) {
                if (items[i] > max2) {
                    max2 = items[i];
                    top2 = i;
                }
            }
            items[top2] = 0;

            // find third top sold items
            int max3 = items[0];
            for (int i=0; i<41; i++) {
                if (items[i] > max3) {
                    max3 = items[i];
                    top3 = i;
                }
            }

            System.out.println("top 1:: " + top1);
            System.out.println("top 2:: " + top2);
            System.out.println("top 3:: " + top3);

            top3tv.setText(Integer.toString(top1) + "\n" + Integer.toString(top2) + "\n" + Integer.toString(top3));


        }
        else {
            System.out.println("here");
            Toast.makeText(this, jsonroot.optString("message"), Toast.LENGTH_SHORT).show();
        }
    }

}

class FreqWord {
    String itemid;

}

