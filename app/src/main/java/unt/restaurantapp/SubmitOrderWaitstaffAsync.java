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
import java.util.List;

/**
 * Created by coltonwood on 4/11/16.
 */
class SubmitOrderWaitstaffAsync extends AsyncTask<Pair<Context, String>, Void, String> {
    //private String urlstring = "http://10.0.2.2/webservice/submitorderwait.php";
    DynamicIP ip = new DynamicIP();
    private String urlstring = "http://" + ip.getIP() + "/webservice/submitorderwait.php";
    URL url;
    ViewMenuWaitstaff caller;
    List<MenuItem> order;
    int tableid;

    SubmitOrderWaitstaffAsync(ViewMenuWaitstaff context, int tableid, List<MenuItem> order) {
        caller = context;
        this.order = order;
        this.tableid = tableid;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        // Check for success tag
        int success;
        String orderstring = "";
        HttpURLConnection dbConnection = null;
        StringBuilder result = new StringBuilder();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());

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

        // HOME TESTING ONLY
        String str = android.os.Build.MODEL;
        if (str.equals("Nexus 6")) {
            urlstring = "http://192.168.1.6/webservice/submitorderwait.php";
        }

        // connect to url
        try {
            url = new URL(urlstring + "?orderstring=" + orderstring + "&status=unclaimed" + "&date=" + formattedDate + "&tableid=" + tableid);
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
