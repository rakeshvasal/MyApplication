package com.example.rakeshvasal.myapplication.Activity.RemindersPackage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rakeshvasal.myapplication.R
import kotlinx.android.synthetic.main.activity_add_update_reminder.*

class AddUpdateReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_reminder)
        btn_add_event.setOnClickListener() {

        }
    }
}
