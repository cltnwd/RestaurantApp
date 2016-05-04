package unt.restaurantapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import unt.restaurantapp.MenuItem;
import unt.restaurantapp.Classes.User;
import unt.restaurantapp.R;

/**
 * Created by Michael on 5/3/2016.
 */
public class UserListAdapter extends ArrayAdapter<User>
{
    public UserListAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
    }

    public UserListAdapter(Context context, int resource, List<User> users)
    {
        super(context, resource, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.userlistrow, null);
        }

        User p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.username);
            TextView tt2 = (TextView) v.findViewById(R.id.firstName);
            TextView tt3 = (TextView) v.findViewById(R.id.lastName);
            TextView tt4 = (TextView) v.findViewById(R.id.email);

            if (tt1 != null)
            {
                tt1.setText(p.getUserName());
            }

            if (tt2 != null)
            {
                tt2.setText(p.getFirstName());
                System.out.println(p.getFirstName());
            }

            if (tt3 != null)
            {
                tt3.setText( p.getLastName());
            }

            if (tt4 != null)
            {
                tt4.setText(p.getEmail());
            }
        }

        return v;
    }
}
