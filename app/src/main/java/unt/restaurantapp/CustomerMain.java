package unt.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class CustomerMain extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Customer");

        Intent intent = getIntent();
        username = intent.getStringExtra("username").toString();

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    public void placeOrder(View view) {
        // TODO: backend for placing an order
    }

    public void playGames(View view) {
        Intent intent = new Intent(this, GamesMain.class);
        startActivity(intent);
    }

    public void requestHelp(View view) {
        Intent intent = new Intent(this, HelpMain.class);
        startActivity(intent);
    }

    public void callAPI(View view) {
        new CallAPISync().execute(new Pair<Context, String>(this, username));
    }
}
