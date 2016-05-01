package unt.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by coltonwood on 4/16/16.
 */
public class TableListAdapter extends ArrayAdapter<Table> {

    public TableListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TableListAdapter(Context context, int resource, List<Table> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.tableitemlistrow, null);
        }

        Table t = getItem(position);

        if (t != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tableid);
            TextView tt2 = (TextView) v.findViewById(R.id.tablestatus);

            if (tt1 != null) {
                tt1.setText("Table "+ Integer.toString(t.getId()));
            }

            if (tt2 != null) {
                tt2.setText(t.getStatus());
            }
        }

        return v;
    }

}