package unt.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loginSubmit(View view) {

        // log in as different users
        if (view.getId() == R.id.customerloginbtn) {
            Intent intent = new Intent(this, CustomerMain.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.waitstaffloginbtn) {
            Intent intent = new Intent(this, WaitstaffMain.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.kitchenloginbtn) {
            Intent intent = new Intent(this, KitchenMain.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.managerloginbtn) {
            Intent intent = new Intent(this, ManagerMain.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Error. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

}
