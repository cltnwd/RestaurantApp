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
class GetBillAsync extends AsyncTask<Pair<Context, String>, Void, String> {
    StaticIP ip = new StaticIP();
    private String urlstring = "http://"+ ip.getIP() + "/webservice/getbill.php";
    URL url;
    EditTableActivity caller;
    int tableid;

    GetBillAsync(EditTableActivity context, int tableid) {
        caller = context;
        this.tableid = tableid;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection dbConnection = null;

        // connect to url
        try {
            urlstring = urlstring + "?tableid=" + tableid;
            url = new URL(urlstring);
            System.out.println(urlstring);
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
        caller.editBill(result);
    }
}
