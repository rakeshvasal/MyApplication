package com.example.rakeshvasal.myapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.Custom_Adapters.Search_Custom_Adapter;
import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.RowItems.Search_Row_Item;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 9/17/2016.
 */
public class SearchPage extends ActionBarActivity {
    Toolbar toolbar;
    EditText et_search_text;
    ImageView img_search;
    ListView lv;
    String search;
    List<Search_Row_Item> search_item = new ArrayList<>();
    Search_Custom_Adapter custom_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        init();
        setDataInList();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.backbtn));
        getSupportActionBar().setTitle("Search Restaurant");
        et_search_text = (EditText) findViewById(R.id.et_search);
        img_search = (ImageView) findViewById(R.id.img_search);
        lv = (ListView) findViewById(R.id.search_list);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_search_text.getText().toString().equalsIgnoreCase("")) {
                    if (Utils.is_Connected_To_Internet(SearchPage.this)) {
                        search = et_search_text.getText().toString();
                        et_search_text.setText("");
                        Intent i = new Intent(SearchPage.this, SelectSearchPopup.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Searchtext", search);
                        i.putExtras(bundle);
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Keyword or Name.", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void setDataInList() {
        List<Search_Row_Item> search_item = new ArrayList<>();
        String name = "", id = "", locality = "";

        DatabaseHelper helper = new DatabaseHelper(SearchPage.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] Column = new String[]{DatabaseHelper.RESTAURANT_NAME, DatabaseHelper.RESTAURANT_ID, DatabaseHelper.RESTAURANT_LOCALITY};

        Cursor cursor = db.query(DatabaseHelper.RESTAURANT_TABLE, Column, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Search_Row_Item search_row_item = new Search_Row_Item(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESTAURANT_LOCALITY)));
                search_item.add(search_row_item);
            }
            cursor.close();

        }
        db.close();
        custom_adapter = new Search_Custom_Adapter(SearchPage.this, R.layout.search_list_item, search_item);
        lv.setAdapter(custom_adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

        if (requestCode == 1) {
            setDataInList();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchPage.this, Dashboard.class);
        startActivity(intent);


    }

}


