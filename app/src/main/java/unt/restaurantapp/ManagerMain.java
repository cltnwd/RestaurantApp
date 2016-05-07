package unt.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Toast;

public class  ManagerMain extends AppCompatActivity {

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manager");

        // reading from shared prefs
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String currentFname = prefs.getString("fname_manager", null);
        if (prefs.getBoolean("isLoggedIn_manager", false)) {
            getSupportActionBar().setTitle("Manager: " + currentFname);
        }
    }

    public void manageMenu(View view)
    {
        Intent intent = new Intent(this, ManagerMenuView.class);
        startActivity(intent);
    }
    public void manageUsers(View view)
    {
        Intent intent = new Intent(this, ViewUsers.class);
        startActivity(intent);
    }


    public void manageTables(View view) {
        Intent intent = new Intent(this, ManageTablesActivity.class);
        startActivity(intent);
    }

    public void viewStats(View view) {
        Intent intent = new Intent(this, ViewStatsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waitstaff, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        if (item.getItemId() == R.id.action_Logout) {

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            if (prefs.getString("username_manager", null) == null) {
                Toast.makeText(this, "You aren't signed in!", Toast.LENGTH_SHORT).show();
            }

            else {
                // clear preferences
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("username_manager", null);
                editor.putString("fname_manager", null);
                editor.putBoolean("isLoggedIn_manager", false);
                editor.apply();


                // make sure log out was successful
                if (prefs.getString("username_manager", null) == null) {
                    Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Welcome!");
                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
