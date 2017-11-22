package com.example.rakeshvasal.myapplication.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.R;


/**
 * Created by User on 9/19/2016.
 */
public class Comments extends BaseFragment {

    EditText et_comments;
    ImageView add_comment;
    ListView lv_comments;
    String comment, res_id;
    boolean insert_status;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_comments, container, false);
        init(root);
        update_list();

            res_id = getArguments().getString("res_id");


        return root;
    }

    private void init(View root) {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.backbtn));*/
        et_comments = (EditText) root.findViewById(R.id.et_commemts);
        add_comment = (ImageView) root.findViewById(R.id.add_comment);
        lv_comments = (ListView) root.findViewById(R.id.lv_comments);

        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = et_comments.getText().toString();

                DatabaseHelper helper = new DatabaseHelper(getActivity());
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

        DatabaseHelper helper = new DatabaseHelper(getActivity());
        final SQLiteDatabase db = helper.getWritableDatabase();
        ArrayAdapter<String> baseAdapter;
        String[] content;
        final String[] memo_data = new String[]{DatabaseHelper.RESTAURANT_ID, DatabaseHelper.COMMENTS_TEXT};
        Cursor cursor3 = db.query(DatabaseHelper.COMMENTS_TABLE, memo_data, DatabaseHelper.RESTAURANT_ID + "='" + res_id + "'", null, null, null, null);
        content = new String[cursor3.getCount()];

        int i = 0;

        while (cursor3.moveToNext()) {
            content[i] = (cursor3.getPosition() + 1) + ". " + cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.COMMENTS_TEXT));
            i++;
        }
        cursor3.close();
        db.close();
        baseAdapter = new ArrayAdapter<>(getActivity(), R.layout.memo_list_items, R.id.memo_content, content);
        lv_comments.setAdapter(baseAdapter);
        baseAdapter.notifyDataSetChanged();
    }


}

