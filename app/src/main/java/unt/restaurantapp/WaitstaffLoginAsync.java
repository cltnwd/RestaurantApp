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

/**
 * Created by Michael on 5/4/2016.
 */
public class WaitstaffLoginAsync extends AsyncTask<Pair<Context, String>, Void, String>
{
    String fname, lname, username, email, password;

    StaticIP ip = new StaticIP();
    private String urlstring = "http://"+ ip.getIP() + "/webservice/waitLogin.php";
    URL url;
    WaitstaffLoginActivity caller;

    WaitstaffLoginAsync(WaitstaffLoginActivity context, String username, String password)
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
