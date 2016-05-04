package unt.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by coltonwood on 4/16/16.
 */
public class EditMenuListAdapter extends ArrayAdapter<MenuItem> {

    public EditMenuListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public EditMenuListAdapter(Context context, int resource, List<MenuItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.editmenuitemlistrow, null);
        }

        MenuItem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.itemname);
            Switch ss = (Switch) v.findViewById(R.id.onoff);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (ss != null) {

                if (p.isAvailable()) {
                    ss.setChecked(true);
                }
                else {
                    ss.setChecked(false);
                }
            }
        }

        return v;
    }

}