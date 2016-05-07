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

public class KitchenLogin extends AppCompatActivity
{

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";
    EditText usernameET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_login);


        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
    }

    public void loginKitchen(View view)
    {

        if (usernameET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }
        else if (passwordET.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            new KitchenLoginAsync(this, usernameET.getText().toString(), passwordET.getText().toString()).execute();
        }
    }
    public void registerChef(View view)
    {
        Intent intent = new Intent(this, ManagerCheck.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void parseData(String jsonstring)
    {

        JSONObject root = null;

        try
        {
            root = new JSONObject(jsonstring);
            Toast.makeText(this, root.optString("message"), Toast.LENGTH_SHORT).show();

            // successful login
            if (root.optInt("success") == 1)
            {

                // save fname of user
                String fname = root.optString("fname");
                System.out.println("FNAME::" + fname);

                // log user in, store fname
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("username", fname);
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                // go back to main
                Intent intent = new Intent(this, KitchenMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        } catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to database. Please try again.", Toast.LENGTH_SHORT).show();
        }


    }
}
