package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;


/**
 * Created by User on 9/18/2016.
 */
public class PopUp extends Activity {
    ImageView comments, Location;
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

        Intent i = getIntent();
        if (i != null) {
            lat = i.getStringExtra("lat");
            longitude = i.getStringExtra("long");
            res_id = i.getStringExtra("Restaurant_id");
            res_name = i.getStringExtra("Restaurant_name");
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

