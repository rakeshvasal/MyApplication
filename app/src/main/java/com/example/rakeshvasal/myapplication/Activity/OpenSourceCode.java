package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.rakeshvasal.myapplication.R;

/**
 * Created by Axisvation on 7/27/2017.
 */

public class OpenSourceCode extends WebViewClient {

    public static void openCode(Activity activity, String classname) {
        LayoutInflater li = LayoutInflater.from(activity);
        View promptsView = li.inflate(R.layout.source_code, null);
        final WebView wvHelp = (WebView) promptsView.findViewById(R.id.wv);
        wvHelp.loadUrl("file:///android_asset/declaration/" + classname + ".txt");
        wvHelp.clearCache(true);
        wvHelp.clearHistory();
        wvHelp.getSettings().setJavaScriptEnabled(true);
        wvHelp.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvHelp.getSettings().setDisplayZoomControls(false);
    }
}
