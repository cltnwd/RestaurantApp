package unt.restaurantapp;

import android.widget.ArrayAdapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by coltonwood on 4/16/16.
 */
public class MenuListAdapter extends ArrayAdapter<MenuItem> {

    public MenuListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public MenuListAdapter(Context context, int resource, List<MenuItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.menuitemlistrow, null);
        }

        MenuItem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.itemname);
            TextView tt2 = (TextView) v.findViewById(R.id.itemdesc);
            TextView tt3 = (TextView) v.findViewById(R.id.itemprice);
            TextView tt4 = (TextView) v.findViewById(R.id.itemcalories);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null) {
                tt2.setText(p.getDescription());
                System.out.println(p.getDescription());
            }

            if (tt3 != null) {
                tt3.setText("$"+String.format("%.2f", p.getPrice()));
            }

            if (tt4 != null) {
                tt4.setText(Integer.toString(p.getCalories()) + " Calories");
            }
        }

        return v;
    }

}