package com.example.rakeshvasal.myapplication.Activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {

    private String tag;
    protected Typeface font;
    private ProgressDialog progressDialog;
    final int REQUEST_ID_MULTIPLE_PERMISSIONS = 2;
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

    private void FacebookKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.rakeshvasal.myapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int call_phone = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
        int read_contacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int write_contacts = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);
        int sms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int phone_state = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int finger_print = ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (read_contacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_CONTACTS);
        }
        if (write_contacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_CONTACTS);
        }
        if (call_phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CALL_PHONE);
        }
        if (sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.SEND_SMS);
        }
        if (phone_state != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (finger_print != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.USE_FINGERPRINT);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
