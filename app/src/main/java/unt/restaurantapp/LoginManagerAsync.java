package unt.restaurantapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import unt.restaurantapp.Classes.Manager;

/**
 * Created by Michael on 5/2/2016.
 */
public class LoginManagerAsync extends AsyncTask<Pair<Context, String>, Void, String>
{
    String fname, lname, username, email, password;

    private String urlstring = "http://10.0.3.2/webservice/ManagerLogin.php";
    URL url;
    ManagerLoginActivity caller;

    LoginManagerAsync(ManagerLoginActivity context, String username, String password)
    {
        caller = context;
        this.username = username;
        this.password = password;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params)
    {

        // Check for success tag
        HttpURLConnection dbConnection = null;
        StringBuilder result = new StringBuilder();

        Log.d("request!", "starting");

        // connect to url
        try
        {

            url = new URL(urlstring + "?username=" + username + "&password=" + password);
            System.out.println(url);
            dbConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e)
        {
            e.printStackTrace();
            Log.d("error::", "error connecting to url");
        }


        try
        {
            // pull data from url
            InputStream in = new BufferedInputStream(dbConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // build json string line by line from the input stream from url
            String line;
            while ((line = reader.readLine()) != null)
            {
                result.append(line);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
            Log.d("error::", "error building json string");
        } finally
        {
            dbConnection.disconnect();
        }

        return result.toString();
    }


    @Override
    protected void onPostExecute(String result)
    {
        System.out.println(result);
        caller.parseData(result);
    }
}