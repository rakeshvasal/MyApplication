package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;


/**
 * Created by User on 9/18/2016.
 */
public class PopUp extends Activity {
    ImageView comments, Location;
    TextView rest_name,res_address,res_locality,res_pincode,res_city,res_contact_nos;
    String lat, longitude, res_id,res_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inflating_layout);
        init();
    }

    private void init() {
        comments = (ImageView) findViewById(R.id.comments);
        Location = (ImageView) findViewById(R.id.Location);
        rest_name = (TextView) findViewById(R.id.res_name);
        res_address = (TextView) findViewById(R.id.res_address);
        res_city = (TextView) findViewById(R.id.res_city);
        res_locality = (TextView) findViewById(R.id.res_locality);
        res_contact_nos = (TextView) findViewById(R.id.res_contact_nos);
        res_pincode = (TextView) findViewById(R.id.res_pincode);


        Intent i = getIntent();
        if (i != null) {
            lat = i.getStringExtra("lat");
            longitude = i.getStringExtra("long");
            res_id = i.getStringExtra("Restaurant_id");
            res_name = i.getStringExtra("Restaurant_name");

            DatabaseHelper helper = DatabaseHelper.getInstance(getApplicationContext());
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
        }


            Location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.is_Connected_To_Internet(PopUp.this)) {
                        Intent i = new Intent(PopUp.this, Googlemap.class);
                        i.putExtra("lat",lat);
                        i.putExtra("long",longitude);
                        i.putExtra("res_name",res_name);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

                    }
                }
            });

            comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PopUp.this, Comments.class);
                    i.putExtra("res_id",res_id);
                    startActivity(i);
                }
            });
    }
}

