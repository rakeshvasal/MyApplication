package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.R;

import org.w3c.dom.ls.LSInput;

/**
 * Created by Rakeshvasal on 29-Jan-17.
 */

public class ShowLocationList extends Activity {

    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationlist);
        init();
    }

    private void init(){
        listView = (ListView) findViewById(R.id.location_list);
        String[] name;
        ArrayAdapter adapter;
        DatabaseHelper helper = DatabaseHelper.getInstance(ShowLocationList.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] column = new String[]{DatabaseHelper.LOCATION_NAME};
        Cursor cursor = db.query(DatabaseHelper.LOCATION_TABLE, column, null, null, null, null, null, null);
        name = new String[cursor.getCount()];
        int i = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                name[i] = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION_NAME));
            }
        }
        adapter = new ArrayAdapter(ShowLocationList.this, R.layout.support_simple_spinner_dropdown_item,name);
        listView.setAdapter(adapter);

    }
}
