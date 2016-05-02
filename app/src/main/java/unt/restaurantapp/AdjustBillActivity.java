package unt.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.TextView;

public class AdjustBillActivity extends AppCompatActivity {

    TextView newBill, oldBill;
    EditText subtract;
    float newtotal, oldprice;
    int tableid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newBill = (TextView)findViewById(R.id.newBill);
        oldBill = (TextView)findViewById(R.id.oldBill);
        subtract = (EditText)findViewById(R.id.subtract);

        oldprice = getIntent().getFloatExtra("oldprice", 0);
        tableid = getIntent().getIntExtra("tableid", 0);

        if (tableid > 0) {
            getSupportActionBar().setTitle("Adjust Bill for " + tableid);
        }
        else {
            getSupportActionBar().setTitle("Error: tableid invalid");
        }

        oldBill.setText(String.format("%.2f", oldprice));

    }

    public void updateTotal(View view) {
        try {
            newtotal = oldprice - Float.valueOf(subtract.getText().toString());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        if (newtotal < 0) {
            newtotal = 0;
        }
        newBill.setText(String.format("%.2f", newtotal));
    }

    public void updateBill(View view) {
        new SetBillAsync(this, tableid, newtotal).execute();
    }

}
