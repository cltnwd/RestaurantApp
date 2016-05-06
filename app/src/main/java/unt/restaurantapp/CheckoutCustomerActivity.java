package unt.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unt.restaurantapp.EditTableActivity;
import unt.restaurantapp.R;

public class CheckoutCustomerActivity extends AppCompatActivity {

    EditText ccedittext;
    int tableid;
    boolean gotBill = false;

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ccedittext = (EditText)findViewById(R.id.ccnumber);
        tableid = getIntent().getIntExtra("tableid", 1);

        new GetBillCheckoutAsync(this, tableid).execute();
    }

    public void parseData(String jsonString) {

        // create json object from results
        JSONObject jsonroot = null;
        JSONArray jsonitems = null;
        float price=0;
        try {
            jsonroot = new JSONObject(jsonString);

            jsonitems = jsonroot.optJSONArray("posts");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (jsonroot != null) {

            if (jsonroot.optInt("success") == 1) {
                try {
                    price = (float) jsonitems.getJSONObject(0).optDouble("price");
                    gotBill = jsonitems.getJSONObject(0).optBoolean("gotit");
                    getSupportActionBar().setTitle("Checkout: $" + String.format("%.2f", price));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, jsonroot.optString("message"), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else
            Toast.makeText(this, "JSON parse error", Toast.LENGTH_SHORT).show();
    }

    public void completeCheckout(View view) {

        if (ccedittext.getText().toString().isEmpty()) {
            Toast.makeText(this, "Missing credit card number", Toast.LENGTH_SHORT).show();
        }
        else {

            Intent intent = new Intent(this, SurveyActivity.class);
            intent.putExtra("tableid", tableid);
            startActivity(intent);
        }


    }

    public void requestHelp(View view) {
        new SetTableStatusAsync(tableid, "Pay").execute();
        Toast.makeText(this, "Request sent!", Toast.LENGTH_SHORT).show();
    }
}
