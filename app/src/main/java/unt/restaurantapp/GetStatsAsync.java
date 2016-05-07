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
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by coltonwood on 4/11/16.
 */
class GetStatsAsync extends AsyncTask<Pair<Context, String>, Void, String> {

    //private String urlstring = "http://10.0.2.2/webservice/getstats.php";
    StaticIP ip = new StaticIP();
    private String urlstring = "http://" + ip.getIP() + "/webservice/getstats.php";
    URL url;
    ViewStatsActivity caller;

    GetStatsAsync(ViewStatsActivity context) {
        caller = context;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection dbConnection = null;

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());

        // connect to url
        try {
            url = new URL(urlstring + "?date=" + formattedDate);
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
