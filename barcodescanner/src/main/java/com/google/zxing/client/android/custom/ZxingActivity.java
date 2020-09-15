package com.google.zxing.client.android.custom;

import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;


import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivityHandler;
import com.google.zxing.client.android.ViewfinderView;
import com.google.zxing.client.android.camera.CameraManager;

public abstract class ZxingActivity extends AppCompatActivity {
    public CaptureActivityHandler handler;
    public ViewfinderView viewfinderView;
    public LineAnimationView lineAnimationView;
    public FrameView frameView;
    public CameraManager cameraManager;

    public CaptureActivityHandler getHandler() {
        return handler;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public FrameView getFrameView() {
        return frameView;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }


    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {

    }

    public void drawViewfinder() {
        if (viewfinderView != null)
            viewfinderView.drawViewfinder();
        if (frameView != null)
            frameView.drawFrameView();
        if (lineAnimationView != null)
            lineAnimationView.drawLineAnimation();
    }
}
