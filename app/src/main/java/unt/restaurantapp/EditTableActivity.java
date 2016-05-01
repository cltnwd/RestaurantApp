package unt.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditTableActivity extends AppCompatActivity {

    String tablestatus = "OK", newtablestatus="OK";
    int tableid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get intent extras
        tableid = getIntent().getIntExtra("tableid", 1);
        tablestatus = getIntent().getStringExtra("tablestatus");

        getSupportActionBar().setTitle("Table " + Integer.toString(tableid));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.statusOKradio:

                if (checked) {
                    // Pirates are the best
                    newtablestatus = "OK";
                }
                    break;

            case R.id.statushelpradio:
                if (checked) {
                    newtablestatus = "Needs help";
                }
                    break;

            case R.id.statusrefillradio:
                if (checked) {
                    newtablestatus = "Needs refill";
                }
                    break;
        }
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this, ViewMenuWaitstaff.class);
        intent.putExtra("tableid", tableid);
        intent.putExtra("tablestatus", tablestatus);
        startActivity(intent);
    }

    public void updateStatus(View view) {
        new SetTableStatusAsync(tableid, newtablestatus).execute();
        Toast.makeText(EditTableActivity.this, "Status changed!", Toast.LENGTH_SHORT).show();
    }

    public void adjustBill(View view) {

    }
}
