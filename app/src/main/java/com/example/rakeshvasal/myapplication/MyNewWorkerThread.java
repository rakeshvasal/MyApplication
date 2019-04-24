package com.example.rakeshvasal.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.rakeshvasal.myapplication.Activity.MultiThreadingTestActiivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MyNewWorkerThread extends HandlerThread {

    private Handler mUiHandler;
    private Handler mResponseHandler;
    private static final String TAG = MyNewWorkerThread.class.getSimpleName();
    private Map<ImageView, String> mRequestMap = new HashMap<ImageView, String>();
    private Callback mCallback;


    public MyNewWorkerThread(Handler responseHandler, Callback callback) {
        super(TAG);
        mResponseHandler = responseHandler;
        mCallback = callback;
    }


    public void queueTask(String url, int side, ImageView imageView) {
        mRequestMap.put(imageView, url);
        mUiHandler.obtainMessage(side, imageView)
                .sendToTarget();
    }

    public void prepareHandler() {
        mUiHandler = new Handler(getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ImageView imageView = (ImageView) msg.obj;
                String side = msg.what == MultiThreadingTestActiivity.LEFT_SIDE ? "left side" : "right side";
                Log.i(TAG, String.format("Processing %s, %s", mRequestMap.get(imageView), side));
                handleRequest(imageView, msg.what);
                try {
                    msg.recycle(); //it can work in some situations
                } catch (IllegalStateException e) {
                    mUiHandler.removeMessages(msg.what); //if recycle doesnt work we do it manually
                }
                return true;
            }
        });
    }

    private void handleRequest(final ImageView imageView, final int side) {
        String url = mRequestMap.get(imageView);
        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(url).openConnection();
            final Bitmap bitmap = BitmapFactory
                    .decodeStream((InputStream) connection.getContent());
            mRequestMap.remove(imageView);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onImageDownloaded(imageView, bitmap, side);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap, int side);
    }
}
