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
 * Created by Michael on 5/3/2016.
 */
public class GetWaitstaffAsync extends AsyncTask<Pair<Context, String>, Void, String>
{
    DynamicIP ip = new DynamicIP();
    private String urlstring = "http://" + ip.getIP() + "/webservice/viewWaitstaff.php";
    URL url;
    ViewUsers caller;

    GetWaitstaffAsync(ViewUsers context)
    {
        caller = context;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params)
    {
        StringBuilder result = new StringBuilder();
        HttpURLConnection dbConnection = null;

        // connect to url
        try {
            url = new URL(urlstring);
            dbConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("error::", "error connecting to url");
        }

        try {
            // pull data from url
            InputStream in = new BufferedInputStream(dbConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // build json string line by line from the input stream from url
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("error::", "error building json string");
        }
        finally {
            dbConnection.disconnect();
        }

        // return the json string
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        caller.parseData(result, 3);
    }
}
