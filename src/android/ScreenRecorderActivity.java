package com.pepperbean.plugin;


import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
//import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class ScreenRecorderActivity extends Activity {
    private static final int REQUEST_CODE = 1;
    //private static final int STARTREC_CODE = 2;
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorder mRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection ResourceType
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
            MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, captureIntent);
            if (mediaProjection == null) {
                Log.e("@@", "media projection is null");
                return;
            }
            // video size
            final int width = 720;
            final int height = 1280;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "record-" + width + "x" + height + "-" + System.currentTimeMillis() + ".mp4");
            final int bitrate = 6000000;
            mRecorder = new ScreenRecorder(width, height, bitrate, 1, mediaProjection, file.getAbsolutePath());
            mRecorder.start();
//        mButton.setText("Stop Recorder");
            Toast.makeText(this, "Screen recorder is running...", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        }
// else if (requestCode == STARTREC_CODE) {
//            StartRec();
//        }
    }

    public void StartRec() {
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, REQUEST_CODE);
    }


    public void StopRec() {
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
        }
    }

    public void FinishRec() {
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        StopRec();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
        }
    }
}
