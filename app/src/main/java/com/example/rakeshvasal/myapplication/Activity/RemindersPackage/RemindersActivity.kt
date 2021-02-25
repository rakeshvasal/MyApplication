package com.example.rakeshvasal.myapplication.Activity.RemindersPackage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.rakeshvasal.myapplication.R
import kotlinx.android.synthetic.main.activity_reminders.*

class RemindersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)
        btn_add_reminder.setOnClickListener() {
            navigateToCreateRemActivity()
        }
    }

    fun navigateToCreateRemActivity() {

    }
}
