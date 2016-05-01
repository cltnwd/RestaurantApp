package unt.restaurantapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by coltonwood on 4/11/16.
 */
class GetMenuAsync extends AsyncTask<Pair<Context, String>, Void, String> {
    private String urlstring = "http://10.0.2.2/webservice/viewmenu.php";
    URL url;
    ViewMenu caller;

    GetMenuAsync(ViewMenu context) {
        caller = context;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection dbConnection = null;

        // HOME TESTING ONLY
        String str = android.os.Build.MODEL;
        if (str.equals("Nexus 6")) {
            urlstring = "http://192.168.1.6/webservice/viewmenu.php";
            System.out.println(urlstring);
        }

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
        caller.parseData(result);
    }
}
