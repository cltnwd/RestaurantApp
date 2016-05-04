package unt.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewOrderActivity extends AppCompatActivity {

    String orderstring=null, status, newstatus;
    int tableid, realid;
    TextView ordertv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderstring = getIntent().getStringExtra("orderstring");
        status = getIntent().getStringExtra("status");
        tableid = getIntent().getIntExtra("tableid", 0);
        realid = getIntent().getIntExtra("realid", 0);

        ordertv = (TextView)findViewById(R.id.orderstring);
        if (orderstring != null) {
            ordertv.setText("Order items: " + orderstring);
        }
    }

    public void complete(View view) {
        new SetOrderStatusAsync(realid, newstatus).execute();
        Toast.makeText(this, "Order status updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ViewOrdersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.unclaimed:

                if (checked) {
                    newstatus = "unclaimed";
                }
                break;

            case R.id.inprogress:
                if (checked) {
                    newstatus = "inprogress";
                }
                break;

            case R.id.ready:
                if (checked) {
                    newstatus = "ready";
                }
                break;

        }
    }

}
