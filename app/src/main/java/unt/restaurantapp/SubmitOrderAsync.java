package unt.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.Menu;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.testing.http.apache.MockHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by coltonwood on 4/11/16.
 */
class SubmitOrderAsync extends AsyncTask<Pair<Context, String>, Void, String> {
    private String urlstring = "http://10.0.2.2/webservice/submitorder.php";
    URL url;
    ViewMenu caller;
    List<MenuItem> order;

    SubmitOrderAsync(ViewMenu context, List<MenuItem> order) {
        caller = context;
        this.order = order;

    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        // Check for success tag
        int success;
        String orderstring = "";
        HttpURLConnection dbConnection = null;
        StringBuilder result = new StringBuilder();

        // build order string
        for (int i = 0; i < order.size(); i++) {
            orderstring += order.get(i).getItemid();
            orderstring += "+";
        }
        if (orderstring.charAt(orderstring.length()-1) == '+') {
            StringBuilder sb = new StringBuilder(orderstring);
            sb.deleteCharAt(orderstring.length()-1);
            orderstring = sb.toString();
        }
        System.out.println("order: " + orderstring);


        Log.d("request!", "starting");

        // connect to url
        try {
            url = new URL(urlstring + "?orderstring=" + orderstring);
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
        caller.orderSubmitted(result);
    }
}
