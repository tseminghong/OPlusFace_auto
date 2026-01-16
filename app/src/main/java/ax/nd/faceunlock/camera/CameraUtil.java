package ax.nd.faceunlock.camera;

import android.content.Context;
import android.hardware.Camera;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraUtil {

    public static int getFrontFacingCameraId(Context context) {
        return 1; 
    }

    public static Camera.Size calBestPreviewSize(Camera.Parameters parameters, final int width, final int height) {
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes == null) return null;

        // 1. Look for exact match
        for (Camera.Size size : supportedPreviewSizes) {
            if (size.width == width && size.height == height) return size;
        }

        // 2. Look for square (1:1) if width==height requested
        if (width == height) {
            for (Camera.Size size : supportedPreviewSizes) {
                if (size.width == size.height && size.width >= 400) {
                    return size; // Found a decent square resolution
                }
            }
        }

        // 3. Fallback: Sort by area
        Collections.sort(supportedPreviewSizes, (lhs, rhs) -> 
            (lhs.width * lhs.height) - (rhs.width * rhs.height));

        for (Camera.Size size : supportedPreviewSizes) {
            if (size.width >= width && size.height >= height) {
                return size;
            }
        }
        return supportedPreviewSizes.get(supportedPreviewSizes.size() - 1);
    }
}