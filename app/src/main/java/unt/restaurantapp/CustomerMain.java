package unt.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerMain extends AppCompatActivity {

    String username;
    private int timesPlayed = 0;
    private Menu menu;
    Button loginButton;
    String MY_PREFS_NAME = "restaurant_app_shared_preferences";
    int TABLE_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome!");

        loginButton = (Button)findViewById(R.id.registerLogin);

        // check if user is logged in
        checkIfLoggedIn();

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    public void checkIfLoggedIn() {

        // reading from shared prefs
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String currentFname = prefs.getString("fname", null);
        if (prefs.getBoolean("isLoggedIn", false)) {
            getSupportActionBar().setTitle("Welcome, " + currentFname + "!");
            loginButton.setEnabled(false);
        }
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this, ViewMenu.class);
        startActivity(intent);
    }

    public void playGames(View view) {
        Intent intent;
        if(timesPlayed < 2)
        {
            intent = new Intent(this, GamesMain.class);
            timesPlayed++;
        }
        else
        {
            intent = new Intent(this, GameLinks.class);
        }
        startActivity(intent);
    }

    public void registerLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void checkoutBtn(View view) {
        new GetOrderStatusAsync(this, TABLE_ID).execute();
    }

    public void parseData(String jsonString) {

        // create json object from results
        JSONObject jsonroot = null;
        try {
            jsonroot = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (jsonroot != null) {
            if (jsonroot.optInt("success") == 1) {
                checkout();
            } else
                Toast.makeText(this, jsonroot.optString("message"), Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "JSON parse error", Toast.LENGTH_SHORT).show();
    }

    public void checkout() {
        Intent intent = new Intent(this, CheckoutCustomerActivity.class);
        intent.putExtra("tableid", TABLE_ID);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_Logout) {

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            if (prefs.getString("username", null) == null) {
                Toast.makeText(this, "You aren't signed in!", Toast.LENGTH_SHORT).show();
            }

            else {
                // clear preferences
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("username", null);
                editor.putString("fname", null);
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // make sure log out was successful
                if (prefs.getString("username", null) == null) {
                    Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Welcome!");
                    loginButton.setEnabled(true);
                }
            }
        }

        if (item.getItemId() == R.id.action_refill) {
            new SetTableStatusAsync(TABLE_ID, "Refill").execute();
            Toast.makeText(getBaseContext(), "Request sent!", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.action_Help) {
            new SetTableStatusAsync(TABLE_ID, "Help").execute();
            Toast.makeText(getBaseContext(), "Request sent!", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

}
