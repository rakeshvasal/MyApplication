package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by Axisvation on 7/27/2017.
 */

public class OpenSourceCode extends BaseFragment {

    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.source_code,container,false);
        mContext = getActivity();
        AssetManager assetManager = mContext.getAssets();

        //File file = new File("/assests/java/Device_Info.txt");
        try {
            assetManager.open("/java/Device_Info.txt");
            //assetManager.open(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return root;
    }


}
