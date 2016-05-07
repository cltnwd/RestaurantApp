package unt.restaurantapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class WaitstaffLoginActivity extends AppCompatActivity
{

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";
    EditText usernameET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitstaff_login);


        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
    }

    public void loginWaiter(View view)
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
            new WaitstaffLoginAsync(this, usernameET.getText().toString(), passwordET.getText().toString()).execute();
        }
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
                String username = root.optString("username");
                System.out.println("FNAME::" + fname);

                // log user in, store fname
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("username_wait", username);
                editor.putString("fname_wait", fname);
                editor.putBoolean("isLoggedIn_wait", true);
                editor.apply();

                // go back to main
                Intent intent = new Intent(this, ViewTablesActivity.class);
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

