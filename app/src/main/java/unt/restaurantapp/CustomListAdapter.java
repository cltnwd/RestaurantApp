package unt.restaurantapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by coltonwood on 4/16/16.
 */
public class CustomListAdapter extends ArrayAdapter<MenuItem> {

    public CustomListAdapter(Context context, int resource) {
        super(context, resource);
    }
}
