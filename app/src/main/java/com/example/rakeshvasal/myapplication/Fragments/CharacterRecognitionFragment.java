package com.example.rakeshvasal.myapplication.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Activity.CameraActivity;
import com.example.rakeshvasal.myapplication.Activity.Image_Capture_Location;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.ServiceCalls.MakeServiceCall;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Rakeshvasal on 21-Nov-17.
 */

public class CharacterRecognitionFragment extends BaseFragment {

    String filepath = "https://firebasestorage.googleapis.com/v0/b/myapplication-8f68b.appspot.com/o/OCRData%2Feng.traineddata?alt=media&token=8f1b70de-a168-489e-8274-02db321fa52c";
    private TessBaseAPI mTess;
    String datapath = "", language;
    Bitmap image;
    Context context;
    private ProgressDialog pDialog;
    private static final int CAMERA_CUSTOM_CAPTURE = 1;
    String image_path;
    Button scanimg,camera;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    public CharacterRecognitionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_image_analysis, container, false);
        Button scan = (Button) root.findViewById(R.id.scan);
        scan.setVisibility(View.GONE);
        scanimg = (Button) root.findViewById(R.id.scanimg);
        camera = (Button) root.findViewById(R.id.camera);
        context = getActivity();
        language = "eng";
        datapath = context.getFilesDir() + "/tesseract/";
        mTess = new TessBaseAPI();

        //image = BitmapFactory.decodeResource(context.getResources(), R.drawable.lt02933546);
        scanimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                new Task().execute();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(intent, CAMERA_CUSTOM_CAPTURE);
            }
        });

        checkFile(new File(datapath + "tessdata/"));

        return root;

    }

    private void initializeTess() {
        if (new File(datapath + "tessdata/").exists()) {
            mTess.init(datapath, language);
            //processImage();
        }
    }

    private void checkFile(File dir) {
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles();
        }
        if (dir.exists()) {
            String datafilepath = datapath + "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            } else {
                initializeTess();

            }
        }
    }

    private void copyFiles() {
        try {

            new DownloadFileFromURL(context, filepath).execute();

            // processImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CUSTOM_CAPTURE) {
            if (resultCode == RESULT_OK) {
                image_path = data.getStringExtra("image_path");
                try {
                    scanimg.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Background Async Task to download file
     */
    public class DownloadFileFromURL extends AsyncTask<String, String, String> {

        Context mContext;
        String fileurl = "";

        public DownloadFileFromURL(Context context, String url) {
            this.mContext = context;
            this.fileurl = url;
        }

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();

        }


        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(fileurl);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                String filepath = datapath + "/tessdata/eng.traineddata";
                OutputStream output = new FileOutputStream(filepath);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);
            pDialog.dismiss();
            scanimg.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getActivity(), CameraActivity.class);
            startActivityForResult(intent, CAMERA_CUSTOM_CAPTURE);
            initializeTess();

        }

    }

    public class Task extends AsyncTask<String, String, String> {
        TessBaseAPI baseAPI = new TessBaseAPI();
        String result = "";

        @Override
        protected String doInBackground(String... params) {
            processImage();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            closeProgressDialog();
            shortToast(""+result);
            Log.d("OCRResult",result);
            /*baseAPI.clear();*/
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            closeProgressDialog();
            //baseAPI.clear();
        }

        public void processImage() {


            File file = new File(image_path);
            //image = BitmapFactory.decodeResource(context.getResources(), R.drawable.lt02933546);

            //mTess.setImage(image);
            mTess.setImage(file);
            result = mTess.getUTF8Text();
            Log.d("OCR", result);
        }
    }





}
