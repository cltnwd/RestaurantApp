package unt.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditTableActivity extends AppCompatActivity {

    String tablestatus = "OK", newtablestatus="OK";
    int tableid;

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get intent extras
        tableid = getIntent().getIntExtra("tableid", 1);
        tablestatus = getIntent().getStringExtra("tablestatus");

        getSupportActionBar().setTitle("Table " + Integer.toString(tableid));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.statusOKradio:

                if (checked) {
                    // Pirates are the best
                    newtablestatus = "OK";
                }
                    break;

            case R.id.statushelpradio:
                if (checked) {
                    newtablestatus = "Help";
                }
                    break;

            case R.id.statusrefillradio:
                if (checked) {
                    newtablestatus = "Refill";
                }
                    break;

            case R.id.statuspaidradio:
                if (checked) {
                    newtablestatus = "OK";
                    new SetBillAsync(tableid, 0).execute();
                    new SetBillStatusAsync(tableid, "paid").execute();

                    // clear preferences
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("username", null);
                    editor.putString("fname", null);
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();
                }

        }
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this, ViewMenuWaitstaff.class);
        intent.putExtra("tableid", tableid);
        intent.putExtra("tablestatus", tablestatus);
        startActivity(intent);
    }

    public void updateStatus(View view) {
        new SetTableStatusAsync(tableid, newtablestatus).execute();
        Toast.makeText(EditTableActivity.this, "Status changed!", Toast.LENGTH_SHORT).show();
    }

    public void adjustBill(View view) {
        // TODO: call GetBillAsync
        new GetBillAsync(this, tableid).execute();
    }

    public void editBill(String jsonString) {

        float ordertotal = 0;

        // create json object from results
        JSONObject jsonroot;
        JSONArray jsonitems = null;
        try {
            jsonroot = new JSONObject(jsonString);

            // get the photo objects
            jsonitems = jsonroot.optJSONArray("posts");

            // get the names of the items
            if (jsonitems != null) {
                for (int i = 0; i < jsonitems.length(); i++) {
                    ordertotal += jsonitems.getJSONObject(i).optDouble("price");
                }
                if (ordertotal > 0) {
                    Intent intent = new Intent(this, AdjustBillActivity.class);
                    intent.putExtra("oldprice", ordertotal);
                    intent.putExtra("tableid", tableid);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "No bill available", Toast.LENGTH_SHORT).show();
                    new SetBillStatusAsync(tableid, "paid").execute();
                }
            }
            else {
                Toast.makeText(this, "No available bill(s)", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("logcat", "error converting string to json");
        }
    }
}
