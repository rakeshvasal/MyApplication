package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Activity.PopUp;
import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.RowItems.Search_Row_Item;

import java.util.List;


/**
 * Created by User on 9/18/2016.
 */
public class Search_Custom_Adapter extends ArrayAdapter<Search_Row_Item> {
    Activity activity;
    int resource;
    private List<Search_Row_Item> search_item = null;

    public Search_Custom_Adapter(Activity activity, int search_list_item, List<Search_Row_Item> search_item) {
        super(activity, search_list_item, search_item);
        this.activity = activity;
        this.resource = search_list_item;
        this.search_item = search_item;
    }

    public int getCount() {
        return search_item.size();
    }

    public class viewholder {
        TextView restaurant_name, restaurant_id, locality;
        LinearLayout ll_main;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View vi = convertView;
        viewholder viewholder = new viewholder();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            vi = inflater.inflate(R.layout.search_list_item, null);
            viewholder.ll_main = (LinearLayout) vi.findViewById(R.id.ll_main);
            viewholder.restaurant_id = (TextView) vi.findViewById(R.id.tv_restaurant_id);
            viewholder.restaurant_name = (TextView) vi.findViewById(R.id.tv_restaurant_name);
            viewholder.locality = (TextView) vi.findViewById(R.id.tv_restaurant_locality);
            vi.setTag(viewholder);
        } else {

            viewholder = (viewholder) vi.getTag();
        }
        final Search_Row_Item row_item = getItem(position);

        //viewholder.restaurant_id.setText(row_item.getRestaurant_id());
        viewholder.restaurant_id.setText(row_item.getRestaurant_name());
        viewholder.locality.setText(row_item.getLocality());

        //final Search_Row_Item.viewholder finalViewholder = viewholder;

        viewholder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat="",longitude="";
                String[] column = new String[]{DatabaseHelper.RESTAURANT_ID,DatabaseHelper.RESTAURANT_LATITUDE,DatabaseHelper.RESTAURANT_LONGITUDE};

                DatabaseHelper helper = new DatabaseHelper(activity);
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.query(DatabaseHelper.RESTAURANT_TABLE, column, DatabaseHelper.RESTAURANT_ID + "='" + row_item.getRestaurant_id() + "'", null, null, null, null, null);

                while (cursor.moveToNext()){
                    lat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_LATITUDE));
                    longitude = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_LONGITUDE));

                }

                Intent intent = new Intent(activity, PopUp.class);
                intent.putExtra("Restaurant_id", row_item.getRestaurant_id());
                intent.putExtra("Restaurant_name", row_item.getRestaurant_name());
                intent.putExtra("lat",lat);
                intent.putExtra("long",longitude);
                activity.startActivity(intent);


            }
        });
        return vi;


    }

}
