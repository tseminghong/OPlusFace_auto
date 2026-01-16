package ax.nd.faceunlock.util;

import android.util.Log;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FaceDebug {
    private static final String TAG = "FaceProvider_DEBUG";

    public static void log(String method, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(">>> CALL: ").append(method).append("(\n");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                sb.append("  [").append(i).append("]: ");
                Object arg = args[i];
                if (arg == null) sb.append("null");
                else sb.append(arg.toString());
                sb.append("\n");
            }
        }
        sb.append(")");
        Log.e(TAG, sb.toString());
    }

    public static void logReturn(String method, Object result) {
        Log.e(TAG, "<<< RETURN: " + method + " -> " + result);
    }
    
    public static void dumpInterface(Object obj) {
        if (obj == null) return;
        Log.e(TAG, "--- INSPECTING INTERFACE ---");
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().startsWith("on")) {
                Log.e(TAG, "  " + m.getName());
            }
        }
    }
}