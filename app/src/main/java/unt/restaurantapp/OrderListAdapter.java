package unt.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by coltonwood on 4/16/16.
 */
public class OrderListAdapter extends ArrayAdapter<Orders> {

    public OrderListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public OrderListAdapter(Context context, int resource, List<Orders> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.orderitemlistrow, null);
        }

        final Orders p = getItem(position);

        TextView tt1;
        TextView tt2;
        TextView tt3;

        if (p != null) {
            tt1 = (TextView) v.findViewById(R.id.tableid);
            tt2 = (TextView) v.findViewById(R.id.orderstring);
            tt3 = (TextView) v.findViewById(R.id.status);


            if (tt1 != null) {
                tt1.setText("Table " + Integer.toString(p.getTableid()));
            }

            if (tt2 != null) {
                tt2.setText(p.getOrderstring());
            }

            if (tt3 != null) {
                tt3.setText(p.getStatus());
            }
        }


        return v;
    }

}