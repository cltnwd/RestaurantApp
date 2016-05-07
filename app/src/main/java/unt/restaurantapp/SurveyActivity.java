package unt.restaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {

    String MY_PREFS_NAME = "restaurant_app_shared_preferences";

    int choice = 3, receipt = 0;
    int tableid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableid = getIntent().getIntExtra("tableid", 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: logout and all that
                new SetBillStatusAsync(tableid, "paid").execute();
                new SetBillAsync(tableid, 0).execute();
                new SetTableStatusAsync(tableid, "OK").execute();

                // get prefs
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                if (prefs.getString("username", null) == null) {
                    finish();
                }

                else {

                    // idk
                }

                // TODO: check amount of visits, apply discount

                if (prefs.getString("username", null) != null) {
                    // clear preferences
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("username", null);
                    editor.putString("fname", null);
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();
                }

                Intent intent = new Intent(getBaseContext(), CustomerMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.one:
                if (checked) {
                    choice = 1;
                }
                break;

            case R.id.two:
                if (checked) {
                    choice = 2;
                }
                break;

            case R.id.three:
                if (checked) {
                    choice = 3;
                }
                break;

            case R.id.four:
                if (checked) {
                    choice = 4;
                }
                break;

            case R.id.five:
                if (checked) {
                    choice = 5;
                }
                break;

            case R.id.skip:
                if (checked) {
                    choice = 6;
                }
                break;
        }
    }

    public void onRadioButtonClicked2(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.email:
                if (checked) {
                    receipt = 1;
                    Toast.makeText(getBaseContext(), "This is where you would enter your email", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.print:
                if (checked) {
                    receipt = 2;
                }
                break;

            case R.id.none:
                if (checked) {
                    receipt = 3;
                }
                break;

        }
    }
}
