package unt.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerMain extends AppCompatActivity {

    String username;
    private int timesPlayed = 0;
    String MY_PREFS_NAME = "restaurant_app_shared_preferences";
    private Menu menu;
    Button loginButton;
    int TABLE_ID = 2;

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
        String currentUsername = prefs.getString("username", null);
        if (currentUsername != null) {
            getSupportActionBar().setTitle("Welcome, " + currentUsername + "!");
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
            new TableStatusAsync(TABLE_ID, "refill").execute();
            Toast.makeText(getBaseContext(), "Request sent!", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.action_Help) {
            new TableStatusAsync(TABLE_ID, "help").execute();
            Toast.makeText(getBaseContext(), "Request sent!", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

}
