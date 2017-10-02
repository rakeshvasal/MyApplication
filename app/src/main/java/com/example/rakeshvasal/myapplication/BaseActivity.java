package com.example.rakeshvasal.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;




public class BaseActivity extends AppCompatActivity {

    private String tag;
    protected Typeface font;
    private ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logInfo("onBackPressed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logInfo("onCreate");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /*font = Typeface.createFromAsset(getAssets(),
                "fonts/opensans_regular.ttf");*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logInfo("onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        logInfo("onNewIntent");
    }

    @Override
    public void finish() {
        super.finish();
        logInfo("finish");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logInfo("onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logInfo("onRestart");
    }

    protected boolean isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logInfo("onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logInfo("onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logInfo("onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logInfo("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logInfo("onStop");
    }

   /* protected void getApp() {

        FragmentActivity activity = this;
        if (activity != null) {
            app =  activity.getApplication();
        }
        return app;
    }*/

    protected String getTag() {
        if (tag == null) {
            tag = getString(R.string.app_name) + " " + getClass().getSimpleName();
        }
        return tag;
    }

    protected void shortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void log(String msg) {
        Log.d(getTag(), msg);
    }

    protected void logInfo(String msg) {
        Log.i(getTag(), msg);
    }

    protected void log(String msg, Throwable tr) {
        Log.e(getTag(), msg, tr);
    }

    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "Please wait", "",
                true, true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void showProgressDialog(String msg) {
        progressDialog = ProgressDialog.show(this, "Please wait", msg,
                true, true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
