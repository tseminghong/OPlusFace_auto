package ax.nd.faceunlock.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import ax.nd.faceunlock.camera.listeners.CameraListener;

public class CameraFaceAuthController {
    private static final String TAG = "CameraFaceAuthController";
    private Context mContext;
    private Handler mHandler;
    private HandlerThread mFaceHandlerThread;
    private Handler mFaceHandler;
    private ServiceCallback mCallback;
    private boolean mIsAuth = false;
    private int mFrameCount = 0;

    public interface ServiceCallback {
        int handlePreviewData(byte[] data, int width, int height);
        void setDetectArea(android.hardware.Camera.Size size);
        void onTimeout(boolean withFace);
        void onCameraError();
    }

    public CameraFaceAuthController(Context context, ServiceCallback callback) {
        mContext = context;
        mCallback = callback;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void start(final int cameraId, final SurfaceTexture dummySurface) {
        mIsAuth = true;
        mFrameCount = 0;
        mFaceHandlerThread = new HandlerThread("face_auth_thread");
        mFaceHandlerThread.start();
        mFaceHandler = new Handler(mFaceHandlerThread.getLooper());

        CameraService.openCamera(cameraId, (i, value) -> {
            Log.e(TAG, "Open Failed: " + i);
            if (mCallback != null) mCallback.onCameraError();
        }, 
        new CameraListener() {
            @Override
            public void onComplete(Object value) {
                 if (value instanceof Camera) {
                     Camera camera = (Camera) value;
                     try {
                         if (dummySurface != null) camera.setPreviewTexture(dummySurface);
                         
                         CameraService.configureAndStartPreview(null, new CameraListener() {
                             @Override
                             public void onComplete(Object val) {
                                 Log.d(TAG, "Auth Preview Started.");
                                 setupBufferedCallback(camera);
                             }
                             @Override public void onError(Exception e) {
                                 if(mCallback != null) mCallback.onCameraError();
                             }
                         });
                     } catch (Exception e) {
                         if (mCallback != null) mCallback.onCameraError();
                     }
                 }
            }
            @Override
            public void onError(Exception e) {
                 if (mCallback != null) mCallback.onCameraError();
            }
        });
    }

    private void setupBufferedCallback(Camera camera) {
        try {
            Camera.Parameters params = camera.getParameters();
            Camera.Size size = params.getPreviewSize();
            int format = params.getPreviewFormat();
            int bitsPerPixel = ImageFormat.getBitsPerPixel(format);
            int bufferSize = (size.width * size.height * bitsPerPixel) / 8;
            
            camera.addCallbackBuffer(new byte[bufferSize]);
            camera.addCallbackBuffer(new byte[bufferSize]);
            camera.addCallbackBuffer(new byte[bufferSize]);

            CameraService.setPreviewCallback((i, val) -> {
                 if (!mIsAuth) return;
                 if (val instanceof byte[]) {
                     final byte[] data = (byte[]) val;
                     if (mFrameCount++ % 10 == 0) Log.d(TAG, "Frame #" + mFrameCount);

                     if (mFaceHandler != null) {
                         mFaceHandler.post(() -> {
                             try {
                                 if (mCallback != null) {
                                     mCallback.handlePreviewData(data, size.width, size.height);
                                 }
                                 if (mIsAuth && camera != null) {
                                     camera.addCallbackBuffer(data);
                                 }
                             } catch (Exception e) { }
                         });
                     }
                 }
            }, true, null); // TRUE = Buffered
            
        } catch (Exception e) {
            Log.e(TAG, "Buffer Setup Failed", e);
        }
    }

    public void stop() {
        mIsAuth = false;
        CameraService.closeCamera(null);
        if (mFaceHandlerThread != null) {
            mFaceHandlerThread.quitSafely();
            mFaceHandlerThread = null;
        }
    }
}