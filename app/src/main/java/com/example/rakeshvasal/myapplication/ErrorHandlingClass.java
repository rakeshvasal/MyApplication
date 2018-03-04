package com.example.rakeshvasal.myapplication;

/**
 * Created by Rakeshvasal on 01-Jan-18.
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.util.Log;

public class ErrorHandlingClass implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler defaultHandler;
    public static String TAG = "ErrorHandlingClass";
    private Activity activity;

    public ErrorHandlingClass(Activity activity) {
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.activity = activity;
    }

    public void uncaughtException(Thread t, Throwable e) {
        final Writer stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stacktrace = stringWriter.toString();
        printWriter.close();
        Log.i(TAG + "-------", stacktrace);
        Log.e("CRASH", stacktrace);
        Log.e("CRASH", e.toString());

        // Chain to the normal uncaught exception handler
        defaultHandler.uncaughtException(t, e);
    }
}