package com.pepperbean.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;

import java.io.File;

public class _CordovaPlugin extends CordovaPlugin {
    private static final int REQUEST_CODE = 1;
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorder mRecorder;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            StartRec();
            callbackContext.success("start screen record");
            return true;
        } else if (action.equals("stop")) {
            StopRec();
            callbackContext.success("stop screen record");
            return true;
        } else {
            return false;
        }
    }

    private void StartRec() {
        if (mMediaProjectionManager == null) {
            mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        }
        Intent intent = mMediaProjectionManager.createScreenCaptureIntent();
        MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(Activity.RESULT_OK, intent);
        if (mediaProjection == null) {
            Log.e("@@", "media projection is null");
            return;
        }
        // video size
        final int width = 1280;
        final int height = 720;
        File file = new File(Environment.getExternalStorageDirectory(),
                "record-" + width + "x" + height + "-" + System.currentTimeMillis() + ".mp4");
        final int bitrate = 6000000;
        mRecorder = new ScreenRecorder(width, height, bitrate, 1, mediaProjection, file.getAbsolutePath());
        mRecorder.start();
    }

    private void StopRec() {
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
        }

    }
}