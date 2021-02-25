package com.example.rakeshvasal.myapplication.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.R;

import java.text.SimpleDateFormat;

public class BaseFragment extends Fragment {

    public static final int PERMS_REQ_READ_PHONE_STATE = 001;
    public static final int PERMS_REQ_READ_CALL_PHONE = 002;
    public static final int PERMS_REQ_READ_CALL_PHONE1 = 001;
    //    public GoogleApiClient mGoogleApiClient;
    private ProgressDialog progressDialog;
    protected Typeface font, fontBold;
    protected int datePickFlag;
    protected int timePickFlag;
    private String tag;
//    private Snackbar snackbar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logInfo("onActivityCreated");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        logInfo("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logInfo("onCreate");
        //font = Typeface.createFromAsset(getActivity().getAssets(),
        //"fonts/opensans_regular.ttf");
        //fontBold = Typeface.createFromAsset(getActivity().getAssets(),
        //"fonts/opensans_semibold.ttf");

    }

    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

  /*  protected NeurologyFoundationApplication getApp() {
        NeurologyFoundationApplication app = null;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            app = (NeurologyFoundationApplication) activity.getApplication();
        }
        return app;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logInfo("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        logInfo("onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logInfo("onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logInfo("onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        logInfo("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        logInfo("onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logInfo("onSaveInstanceState");
    }

    @Override
    public void onStart() {
        super.onStart();
        logInfo("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        logInfo("onStop");
    }

    protected String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }

    public SpannableStringBuilder getSpanStringBuilder(String error) {
        SpannableStringBuilder ssbuilder = null;
        if (error != null) {
            int ecolor = Color.RED; // whatever color you want
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
            ssbuilder = new SpannableStringBuilder(error);
            ssbuilder.setSpan(fgcspan, 0, error.length(), 0);
            // myedittext.setError(ssbuilder);
            return ssbuilder;
        }
        return ssbuilder;
    }

    protected boolean isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }


    public void hideKeyboard(View edt) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getApplicationWindowToken(), 0);
    }

    public void showKeyboard(View edt) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edt, 0);
    }

    protected String getAppTag() {
        if (tag == null) {
            tag = getString(R.string.app_name) + " " + getClass().getSimpleName();
        }
        return tag;
    }

    protected void log(String msg) {
        Log.d(getAppTag(), msg);
    }

    protected void logInfo(String msg) {
        Log.i(getAppTag(), msg);
    }

    protected void log(String msg, Throwable tr) {
        Log.e(getAppTag(), msg, tr);
    }

    protected void shortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "Please wait...", "",
                true, true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    protected void showProgressDialog(String message) {
        progressDialog = ProgressDialog.show(getActivity(), "Please wait",
                message, true, true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    protected void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void longToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    protected boolean checkemptyedittext(EditText editText) {

        if (editText != null) {
            if (editText.getText().toString().equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }
}
