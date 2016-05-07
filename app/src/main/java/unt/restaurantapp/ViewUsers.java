package unt.restaurantapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import unt.restaurantapp.Classes.Customer;
import unt.restaurantapp.Classes.Manager;
import unt.restaurantapp.Classes.User;
import unt.restaurantapp.Classes.UserListAdapter;
import unt.restaurantapp.Classes.Waiter;

public class ViewUsers extends AppCompatActivity
{
    ListView userListView;
    

    List<User> customerList = new ArrayList<>();
    List<User> waiterList= new ArrayList<>();
    List<User> managerList= new ArrayList<>();
    List<User> kitchenList= new ArrayList<>();
    String currentList = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        userListView = (ListView)findViewById(R.id.UserListView);

        new GetUsersAsync(this).execute();
        new GetWaitstaffAsync(this).execute();
        new GetManagersAsync(this).execute();
        new GetKitchenAsync(this).execute();
    }

    public void parseData(String jsonString, int list)
    {

        // create json object from results
        JSONObject jsonroot;
        JSONArray jsonCus = null;
        try
        {
            jsonroot = new JSONObject(jsonString);

            // move down a level
            //JSONObject jsonphoto = jsonroot.optJSONObject("post");

            // get the photo objects
            jsonCus = jsonroot.optJSONArray("posts");


            // print items
            //Toast.makeText(this, items, Toast.LENGTH_LONG).show();

        } catch (JSONException e)
        {
            e.printStackTrace();
            Log.d("logcat", "error converting string to json");
        }

        setUpListView(jsonCus, list);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setUpListView(JSONArray jsonUser, int list)
    {

        if (jsonUser == null)
        {
            return;
        }

        // entrees
        for (int i = 0; i < jsonUser.length(); i++)
        {
            try
            {
                // get info from json
                String fname = jsonUser.getJSONObject(i).optString("fname");
                String lname = jsonUser.getJSONObject(i).optString("lname");
                String userName = jsonUser.getJSONObject(i).optString("username");
                String email = jsonUser.getJSONObject(i).optString("email");
                int id = jsonUser.getJSONObject(i).getInt("id");
                //int visits = jsonCus.getJSONObject(i).getInt("visits");


                // create menu item
                User newUser = new User();
                newUser.setFirstName(fname);
                newUser.setLastName(lname);
                newUser.setEmail(email);
                newUser.setUserName(userName);
                //newUser.setMyRewardPoints(visits);
                newUser.setUserID(id);

                if(list == 1)
                {
                    customerList.add(newUser);
                }
                else if(list == 2)
                {
                    kitchenList.add(newUser);
                }
                else if(list == 3)
                {
                    waiterList.add(newUser);
                }
                else if(list == 4)
                {
                    managerList.add(newUser);
                }


            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void updateUserList(View view)
    {
        UserListAdapter customAdapter;

        if (view.getId() == R.id.customerBtn)
        {
            new GetUsersAsync(this).execute();
            customAdapter = new UserListAdapter(this, R.layout.userlistrow, customerList);
            userListView.setAdapter(customAdapter);
            currentList = "Customer";

        }

        else if (view.getId() == R.id.managerBtn)
        {
            new GetManagersAsync(this).execute();
            customAdapter = new UserListAdapter(this, R.layout.userlistrow, managerList);
            userListView.setAdapter(customAdapter);
            currentList = "Manager";
        }

        else if (view.getId() == R.id.waitstaffBtn)
        {
            new GetWaitstaffAsync(this).execute();
            customAdapter = new UserListAdapter(this, R.layout.userlistrow, waiterList);
            userListView.setAdapter(customAdapter);
            currentList = "Waitstaff";

        }

        else if (view.getId() == R.id.kitchenBtn)
        {
            new GetKitchenAsync(this).execute();
            customAdapter = new UserListAdapter(this, R.layout.userlistrow, kitchenList);
            userListView.setAdapter(customAdapter);
            currentList = "Kitchen";

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_customer, menu);
        return true;
    }


}
