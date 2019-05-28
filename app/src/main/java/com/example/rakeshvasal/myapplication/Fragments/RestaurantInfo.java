package com.example.rakeshvasal.myapplication.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;


/**
 * Created by User on 9/18/2016.
 */
public class RestaurantInfo extends BaseFragment {
    ImageView comments, Location;
    TextView rest_name,res_address,res_locality,res_pincode,res_city,res_contact_nos;
    String lat, longitude, res_id,res_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.inflating_layout, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        comments = (ImageView) root.findViewById(R.id.comments);
        Location = (ImageView) root.findViewById(R.id.Location);
        rest_name = (TextView) root.findViewById(R.id.res_name);
        res_address = (TextView) root.findViewById(R.id.res_address);
        res_city = (TextView) root.findViewById(R.id.res_city);
        res_locality = (TextView) root.findViewById(R.id.res_locality);
        res_contact_nos = (TextView) root.findViewById(R.id.res_contact_nos);
        res_pincode = (TextView) root.findViewById(R.id.res_pincode);


            lat = getArguments().getString("lat");
            longitude =  getArguments().getString("long");
            res_id = getArguments().getString("Restaurant_id");
            res_name =  getArguments().getString("Restaurant_name");

            DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
            SQLiteDatabase db = helper.getWritableDatabase();
            String[] columns = new String[]{DatabaseHelper.RESTAURANT_NAME,DatabaseHelper.RESTAURANT_ADDRESS,DatabaseHelper.RESTAURANT_PINCODE
            ,DatabaseHelper.RESTAURANT_LOCALITY,DatabaseHelper.RESTAURANT_CITY,DatabaseHelper.RESTAURANT_CONTACT_NUMBERS};
            Cursor cursor = db.query(DatabaseHelper.RESTAURANT_TABLE,columns,DatabaseHelper.RESTAURANT_ID + "='" + res_id + "'",null, null, null, null);

            if(cursor!=null){
                while (cursor.moveToNext()){
                    rest_name.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_NAME)));
                    res_address.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_ADDRESS)));
                    res_pincode.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_PINCODE)));
                    res_city.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_CITY)));
                    res_locality.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_LOCALITY)));
                    res_contact_nos.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_CONTACT_NUMBERS)));
                }
            }



            Location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.is_Connected_To_Internet(getActivity())) {


                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Bundle arg = new Bundle();
                        arg.putString("lat", lat);
                        arg.putString("long", longitude);
                        arg.putString("res_name", res_name);
                        Fragment fragment = new Googlemap();
                        fragment.setArguments(arg);
                        //((ViewGroup)getView().getParent()).getId()
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

                    }
                }
            });

            comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle arg = new Bundle();
                    arg.putString("res_id", res_id);
                    Fragment fragment = new Comments();
                    fragment.setArguments(arg);
                    //((ViewGroup)getView().getParent()).getId()
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });
    }
}

