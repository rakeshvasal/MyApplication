package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.R;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Rakeshvasal on 21-Nov-17.
 */

public class CharacterRecognition {


    private TessBaseAPI mTess;
    String datapath = "";
    Bitmap image;
    Activity activity;

    public CharacterRecognition(Activity activity) {

        this.activity = activity;
        String language = "eng";
        datapath = activity.getFilesDir() + "/tesseract/";
        mTess = new TessBaseAPI();

        image = BitmapFactory.decodeResource(activity.getResources(), R.drawable.lt02933546);

        checkFile(new File(datapath + "tessdata/"));
        mTess.init(datapath, language);
        processImage();
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
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = activity.getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);

            if (!file.exists()) {
                throw new FileNotFoundException();
            }
           // processImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processImage() {
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        Log.d("OCR", OCRresult);

    }

}
