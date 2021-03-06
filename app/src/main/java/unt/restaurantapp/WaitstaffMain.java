package unt.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class WaitstaffMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitstaff_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Waitstaff");


    }

    public void viewTables(View view) {
        Intent intent = new Intent(this, ViewTablesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
}
