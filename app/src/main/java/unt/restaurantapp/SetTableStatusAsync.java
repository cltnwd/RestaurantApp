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
class SetTableStatusAsync extends AsyncTask<Pair<Context, String>, Void, String> {
    String status;
    int tableid;

    //private String urlstring = "http://10.0.2.2/webservice/changetablestatus.php";
    StaticIP ip = new StaticIP();
    private String urlstring = "http://" + ip.getIP() + "/webservice/changetablestatus.php";
    URL url;
    RegisterUserActivity caller;

    SetTableStatusAsync(int tableid, String status) {
        this.tableid = tableid;
        this.status = status;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        // Check for success tag
        HttpURLConnection dbConnection = null;
        StringBuilder result = new StringBuilder();

        Log.d("request!", "starting");


        // connect to url
        try {
            System.out.println(urlstring + "?tableid=" + tableid + "+&status=" + status);
            url = new URL(urlstring + "?tableid=" + tableid + "+&status=" + status);
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