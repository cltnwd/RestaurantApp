package unt.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import unt.restaurantapp.Classes.Kitchen;

public class RegisterKitchen extends AppCompatActivity
{

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";
    EditText usernameET, passwordET, emailET, fnameET, lnameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_kitchen);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        //getSupportActionBar().setTitle("Register");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameET = (EditText)findViewById(R.id.username);
        fnameET = (EditText)findViewById(R.id.fname);
        lnameET = (EditText)findViewById(R.id.lname);
        emailET = (EditText)findViewById(R.id.email);
        passwordET = (EditText)findViewById(R.id.password);

    }

    public void registerChef(View view) {

        String fname, lname, username, email, password;
        fname = fnameET.getText().toString();
        lname = lnameET.getText().toString();
        username = usernameET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        if (fname.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter a first name", Toast.LENGTH_SHORT).show();
        }

        else if (lname.isEmpty()) {
            Toast.makeText(this, "Please enter a last name", Toast.LENGTH_SHORT).show();
        }

        else if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }

        else if (email.isEmpty()) {
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
        }

        else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
        }

        else {
            new RegisterKitchenAsync(this, fname, lname, username, email, password).execute();
        }
    }

    public void loginChef(View view) {
        Intent intent = new Intent(this, KitchenLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void chefRegistered(String jsonstring) {

        JSONObject root = null;

        try {
            root = new JSONObject(jsonstring);
            Toast.makeText(this, root.optString("message"), Toast.LENGTH_SHORT).show();

            if (root.optInt("success") == 1) {

                // log user in, store fname
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("username", usernameET.getText().toString());
                editor.putString("fname", fnameET.getText().toString());
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                // go back to waitstaff main
                Intent intent = new Intent(this, KitchenMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to database. Please try again.", Toast.LENGTH_SHORT).show();
        }


    }
}
