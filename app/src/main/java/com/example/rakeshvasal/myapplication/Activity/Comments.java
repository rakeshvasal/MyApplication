package com.example.rakeshvasal.myapplication.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.R;


/**
 * Created by User on 9/19/2016.
 */
public class Comments extends ActionBarActivity {

    EditText et_comments;
    ImageView add_comment;
    ListView lv_comments;
    String comment, res_id;
    boolean insert_status;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Intent i = getIntent();

        if (i != null) {
            res_id = i.getStringExtra("res_id");
        }
        init();
        update_list();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.backbtn));
        et_comments = (EditText) findViewById(R.id.et_commemts);
        add_comment = (ImageView) findViewById(R.id.add_comment);
        lv_comments = (ListView) findViewById(R.id.lv_comments);

        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = et_comments.getText().toString();

                DatabaseHelper helper = new DatabaseHelper(Comments.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.RESTAURANT_ID, res_id);
                contentValues.put(DatabaseHelper.COMMENTS_TEXT, comment);

                insert_status = db.insert(DatabaseHelper.COMMENTS_TABLE, null, contentValues) > 0;
                Log.d("insert_comment", "" + insert_status);
                et_comments.setText("");
                db.close();
                update_list();
            }

        });


    }

    private void update_list() {

        DatabaseHelper helper = new DatabaseHelper(Comments.this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        ArrayAdapter baseAdapter = null;
        String[] content, title;
        final String[] memo_data = new String[]{DatabaseHelper.RESTAURANT_ID, DatabaseHelper.COMMENTS_TEXT};
        Cursor cursor3 = db.query(DatabaseHelper.COMMENTS_TABLE, memo_data, DatabaseHelper.RESTAURANT_ID + "='" + res_id + "'", null, null, null, null);
        content = new String[cursor3.getCount()];


        int i = 0;

        if (cursor3 != null) {
            while (cursor3.moveToNext()) {
                content[i] = (cursor3.getPosition() + 1) + ". " + cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.COMMENTS_TEXT));
                i++;
            }
            cursor3.close();
            db.close();
            baseAdapter = new ArrayAdapter(Comments.this, R.layout.memo_list_items, R.id.memo_content, content);
            lv_comments.setAdapter(baseAdapter);
            baseAdapter.notifyDataSetChanged();

        }
    }


}

