package com.example.rakeshvasal.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.URLModifiers;

public class TestActivity extends BaseActivity implements URLModifiers.getValue {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        URLModifiers modifiers = new URLModifiers(TestActivity.this);
        modifiers.setValueListener(TestActivity.this);
        modifiers.shortenUrl("https://firebasestorage.googleapis.com/v0/b/myapplication-8f68b.appspot.com/o/images%2F20171113_134545.jpg?alt=media&token=0bd7f11b-94b0-455c-b0f8-4b38c2b522fa");
    }

    @Override
    public void setData(String data) {
        logInfo(data);
    }
}
