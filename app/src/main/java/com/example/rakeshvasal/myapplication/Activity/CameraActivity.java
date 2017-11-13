package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.View.CameraPreview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by rakesh.vasal on 6/1/2017.
 */
public class CameraActivity extends BaseActivity {
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    static String assembly, image_path;
    CheckBox flashcheck;
    Camera.Parameters params;

    SQLiteDatabase db;
    SharedPreferences preferences;



    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_camera);

        init();



    }

    private void init() {
        flashcheck = (CheckBox) findViewById(R.id.flash_check);
        boolean camera = checkCameraHardware(CameraActivity.this);
        if (camera) {
            // mCamera.release();
            mCamera = getCameraInstance();
            params = mCamera.getParameters();
            setParameters();
            // mCamera.setParameters(params);
            mCameraPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mCameraPreview);
        } else {
            Toast.makeText(CameraActivity.this, "No Camera", Toast.LENGTH_SHORT).show();
        }
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraPreview.mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            mCamera.takePicture(null, null, mPicture);
                        }
                    }
                });


            }
        });

        flashcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setParameters();
            }
        });
    }

    private void setParameters() {

        List<String> focusModes = params.getSupportedFocusModes();
        try {
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            } else {
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (CameraActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                if (flashcheck.isChecked()) {
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                } else {
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        for(int i=0;i<sizes.size();i++)
        {
            if(sizes.get(i).width > size.width)
                size = sizes.get(i);
        }
        params.setPictureSize(size.width, size.height);

        params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
        params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        params.setExposureCompensation(0);
        params.setPictureFormat(ImageFormat.JPEG);
        params.setJpegQuality(100);
        params.setRotation(90);
        mCamera.setParameters(params);
    }


    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {

        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();

        android.hardware.Camera.getCameraInfo(cameraId, info);

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /**
     *
     *
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
            camera = Camera.open();

        } catch (Exception e) {
            e.printStackTrace();
            releaseCameraAndPreview();
            // cannot get camera or does not exist
        }
        return camera;
    }

    private void releaseCameraAndPreview() {

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        //setValues();
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                //imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.write(data);
                fos.close();
                Intent intent = new Intent();
                String image_name = pictureFile.getName();
                String image_path = pictureFile.getPath();
                intent.putExtra("imagename",image_name );
                intent.putExtra("image_path", image_path);
                setResult(-1, intent);
                releaseCameraAndPreview();
                finish();

            } catch (FileNotFoundException e) {

                e.printStackTrace();
                Intent intent = new Intent();
                setResult(0, intent);
                releaseCameraAndPreview();
                finish();

            } catch (Exception e) {

                e.printStackTrace();
                Intent intent = new Intent();
                setResult(0, intent);
                releaseCameraAndPreview();
                finish();

            }
        }
    };

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/ImageFolder/");
        //File mediaStorageDir = new File("");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + timeStamp + ".jpg");

        return mediaFile;
    }


}
