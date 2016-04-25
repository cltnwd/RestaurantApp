package unt.restaurantapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class GamesMain extends AppCompatActivity {

    Random rand = new Random();
    private int win1 = rand.nextInt(10) + 1;
    private int win2 = win1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pickWinners();

    }
    private void pickWinners()
    {
        while(win2 == win1)
        {
            win1 = rand.nextInt(10) + 1;
        }
    }

    public void buttonOneClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 1) || (win2 == 1))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonTwoClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 2) || (win2 == 2))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonThreeClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 3) || (win2 == 3))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonFourClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 4) || (win2 == 4))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonFiveClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 5) || (win2 == 5))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonSixClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 6) || (win2 == 6))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonSevenClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 7) || (win2 == 7))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonEightClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 8) || (win2 == 8))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonNineClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 9) || (win2 == 9))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

    public void buttonTenClick(View view)
    {
        Button button = (Button)view;
        if((win1 == 10) || (win2 == 10))
        {
            button.setText("WIN!!");
        }
        else
        {
            button.setText("LOSE!!");
        }
    }

}
