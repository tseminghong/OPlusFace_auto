package ax.nd.faceunlock.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

import ax.nd.faceunlock.camera.listeners.CameraListener;
import ax.nd.faceunlock.camera.listeners.ErrorCallbackListener;

public class CameraFaceEnrollController {
    private static final String TAG = "CameraFaceEnrollController";
    private static CameraFaceEnrollController sInstance;
    private Context mContext;
    private Handler mHandler;
    private HandlerThread mEnrollHandlerThread;
    private Handler mEnrollHandler;
    private CameraCallback mCallback;
    private boolean mIsEnrolling = false;
    private int mFrameCount = 0;

    public interface CameraCallback {
        int handleSaveFeature(byte[] data, int width, int height, int angle);
        void handleSaveFeatureResult(int res);
        void onFaceDetected();
        void onTimeout();
        void onCameraError();
        void setDetectArea(Camera.Size size);
    }

    public static CameraFaceEnrollController getInstance(Context context) {
        if (sInstance == null) sInstance = new CameraFaceEnrollController(context);
        return sInstance;
    }

    public static CameraFaceEnrollController getInstance() {
        return sInstance;
    }

    private CameraFaceEnrollController(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void start(CameraCallback callback, int cameraId, Surface previewSurface) {
        Log.d(TAG, "start() called with cameraId: " + cameraId);
        if (mIsEnrolling) stop(null);
        mIsEnrolling = true;
        mCallback = callback;
        mFrameCount = 0;
        
        mEnrollHandlerThread = new HandlerThread("face_enroll_thread");
        mEnrollHandlerThread.start();
        mEnrollHandler = new Handler(mEnrollHandlerThread.getLooper());

        // 1. Open Camera
        CameraService.openCamera(cameraId, new ErrorCallbackListener() {
            @Override
            public void onEventCallback(int i, Object value) {
                Log.e(TAG, "Camera open error: " + i);
                if (mCallback != null) mCallback.onCameraError();
            }
        }, new CameraListener() {
            @Override
            public void onComplete(Object value) {
                if (value instanceof Camera) {
                    // 2. Configure & Start Preview (Synchronous on BG thread)
                    startConfiguredPreview(previewSurface);
                }
            }
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Camera open exception", e);
                if (mCallback != null) mCallback.onCameraError();
            }
        });
    }

    private void startConfiguredPreview(Surface surface) {
        // Calls the new combined method
        CameraService.configureAndStartPreview(surface, new CameraListener() {
            @Override
            public void onComplete(Object value) {
                Log.d(TAG, "Preview Started Successfully. Attaching Callback...");
                attachPreviewCallback();
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Config/Start failed", e);
                if (mCallback != null) mCallback.onCameraError();
            }
        });
    }

    private void attachPreviewCallback() {
        if (mCallback != null) {
            try {
                // We don't have direct access to params here easily without reading again, 
                // but we can trust the Defaults or read if necessary.
                // For area, let's just use defaults or 640x480 if read fails/is slow
                // Or better: Just skip setting detect area in UI for now to prioritize scanning
            } catch (Exception e) {}
        }

        // 3. Set Preview Callback (Non-Buffered for speed/reliability)
        CameraService.setPreviewCallback((i, obj) -> {
            if (!mIsEnrolling || mCallback == null) return;
            if (obj instanceof byte[]) {
                final byte[] data = (byte[]) obj;
                
                // Logging throttling
                if (mFrameCount++ % 30 == 0) Log.d(TAG, "Frame #" + mFrameCount + " size=" + data.length);

                if (mEnrollHandler != null) {
                    mEnrollHandler.post(() -> {
                        try {
                            if (mCallback == null) return;
                            // Assume 640x480 for now or read from CameraService if we cached it
                            // Passing 0,0 usually works if engine handles dynamic size, 
                            // otherwise we should use the size we set in ConfigureCallable (e.g. 640x480)
                            int width = 640; 
                            int height = 480; 
                            
                            // Pass 90 degrees for portrait
                            int res = mCallback.handleSaveFeature(data, width, height, 90);
                            
                            if (res == 0) Log.i(TAG, "Megvii saveFeature SUCCESS!");
                            mCallback.handleSaveFeatureResult(res);
                        } catch (Exception e) {
                            Log.e(TAG, "Enroll processing error", e);
                        }
                    });
                }
            }
        }, false, null); // FALSE = Non-buffered
    }

    public void stop(CameraCallback callback) {
        Log.d(TAG, "stop() called");
        mIsEnrolling = false;
        mCallback = null;
        CameraService.closeCamera(null);
        if (mEnrollHandlerThread != null) {
            mEnrollHandlerThread.quitSafely();
            mEnrollHandlerThread = null;
        }
    }
}