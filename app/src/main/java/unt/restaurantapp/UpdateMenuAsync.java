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
 * Created by coltonwood on 4/11/16.
 */
class UpdateMenuAsync extends AsyncTask<Pair<Context, String>, Void, String> {

    boolean isavailable;
    int avail;
    int itemid;

    //private String urlstring = "http://10.0.2.2/webservice/setbill.php";
    StaticIP ip = new StaticIP();
    private String urlstring = "http://" + ip.getIP() + "/webservice/updatemenu.php";
    URL url;

    UpdateMenuAsync(int itemid, boolean isavailable) {
        this.itemid = itemid;
        this.isavailable = isavailable;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        avail = 0;
        if (isavailable) {
            avail = 1;
        }


        // Check for success tag
        HttpURLConnection dbConnection = null;
        StringBuilder result = new StringBuilder();

        Log.d("request!", "starting");

        // connect to url
        try {
            System.out.println(urlstring + "?itemid=" + itemid+ "+&isavailable=" + avail);
            url = new URL(urlstring + "?itemid=" + itemid + "+&isavailable=" + avail);
            System.out.println(url);
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
        } finally {
            dbConnection.disconnect();
        }

        return result.toString();
    }


    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
    }
}