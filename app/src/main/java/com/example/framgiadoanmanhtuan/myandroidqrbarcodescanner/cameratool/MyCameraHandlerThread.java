package com.example.framgiadoanmanhtuan.myandroidqrbarcodescanner.cameratool;

import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import me.dm7.barcodescanner.core.CameraUtils;
import me.dm7.barcodescanner.core.CameraWrapper;

public class MyCameraHandlerThread extends HandlerThread {
    private static final String LOG_TAG = "CameraHandlerThread";
    private MyBarcodeScannerView mScannerView;

    public MyCameraHandlerThread(MyBarcodeScannerView scannerView) {
        super("CameraHandlerThread");
        this.mScannerView = scannerView;
        this.start();
    }

    public void startCamera(final int cameraId) {
        Handler localHandler = new Handler(this.getLooper());
        localHandler.post(new Runnable() {
            public void run() {
                final Camera camera = CameraUtils.getCameraInstance(cameraId);
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    public void run() {
                        MyCameraHandlerThread.this.mScannerView.setupCameraPreview(
                                CameraWrapper.getWrapper(camera, cameraId));
                    }
                });
            }
        });
    }
}
