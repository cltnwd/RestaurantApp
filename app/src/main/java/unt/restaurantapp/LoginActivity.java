package unt.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET, passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Log in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameET = (EditText)findViewById(R.id.username);
        passwordET = (EditText)findViewById(R.id.password);

    }

    public void loginUser(View view) {

        if (usernameET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }

        else if (passwordET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser(View view) {
        Intent intent = new Intent(this, RegisterLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
