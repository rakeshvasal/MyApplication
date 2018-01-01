package com.example.rakeshvasal.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.Activity.Dashboard;

/**
 * Created by Rakeshvasal on 01-Jan-18.
 */

public class ErrorHandlingClass extends MyApplicationClass {
    // uncaught exception handler variable
    private Thread.UncaughtExceptionHandler defaultUEH;

    // handler listener
    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler =
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    try { // here I do logging of exception to a db
                        Toast toast = Toast.makeText(getBaseContext(),"Sorry Something went wrong", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                      /*  runOnUiThread(new Runnable() {
                            public void run() {
                                Toast toast = Toast.makeText(getBaseContext(),"Sorry Something went wrong", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        });*/
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    PendingIntent myActivity = PendingIntent.getActivity(getBaseContext(),
                            192837, new Intent(getApplicationContext(), Dashboard.class),
                            PendingIntent.FLAG_ONE_SHOT);


                    AlarmManager alarmManager;
                    alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            15000, myActivity);
                    System.exit(2);



                    // re-throw critical exception further to the os (important)
                    defaultUEH.uncaughtException(thread, ex);
                }
            };

    public ErrorHandlingClass() {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        try {
            Toast toast = Toast.makeText(getApplicationContext(),"Sorry Something went wrong", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            /*runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(getBaseContext(),"Slow Internet Connection Issue", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });*/
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // setup handler for uncaught exception
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
    }
}
