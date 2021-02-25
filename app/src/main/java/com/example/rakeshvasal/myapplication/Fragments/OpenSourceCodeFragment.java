package com.example.rakeshvasal.myapplication.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;

import com.example.rakeshvasal.myapplication.R;

/**
 * Created by Axisvation on 7/27/2017.
 */

public class OpenSourceCodeFragment extends DialogFragment {

    Activity mContext;
    WebView vw;
    String filetype = "", filename = "";
    View root;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        filetype = getArguments().getString("file_type");
        filename = getArguments().getString("file_name");
        if (filetype.equalsIgnoreCase("")) {
            //shortToast("No File Type Specified");
        }
        mContext = getActivity();

        /*if (filetype.equalsIgnoreCase("")) {
            //shortToast("No File Name Specified");
        }*/
        LayoutInflater li = LayoutInflater.from(getActivity());
        root = li.inflate(R.layout.source_code, null);
        vw = (WebView) root.findViewById(R.id.wv);
        try {
            if (!filetype.equalsIgnoreCase("")) {
                if (!filename.equalsIgnoreCase("")) {
                    vw.getSettings().setJavaScriptEnabled(true);
                    vw.setClickable(false);
                    //vw.loadUrl("file:///android_asset/" + filetype + "/" + filename);
                    vw.loadUrl(filename);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setView(root)
                // Set Dialog Title
                .setTitle(filename)
                // Set Dialog Message
                //.setMessage("Alert DialogFragment Tutorial")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       // mContext.finish();
                    }
                })

                .create();

    }

}
