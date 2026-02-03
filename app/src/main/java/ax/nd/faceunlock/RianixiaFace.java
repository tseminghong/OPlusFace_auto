package ax.nd.faceunlock;

import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.biometrics.face.SensorProps;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RianixiaFace extends IFace.Stub {
    private static final String TAG = "RianixiaFace";
    private static final int INTERFACE_VERSION = 2;
    private static final String INTERFACE_HASH = "rianixia_hash";

    public RianixiaFace() {
        Log.d(TAG, "RianixiaFace Daemon Initialized");
    }

    @Override
    public int getInterfaceVersion() throws RemoteException {
        return INTERFACE_VERSION;
    }

    @Override
    public String getInterfaceHash() throws RemoteException {
        return INTERFACE_HASH;
    }

    @Override
    public SensorProps[] getSensorProps() throws RemoteException {
        return new SensorProps[0];
    }

    @Override
    public ISession createSession(int sensorId, int userId, ISessionCallback cb) throws RemoteException {
        Log.d(TAG, "createSession called for user: " + userId);
        
        RianixiaSession session = new RianixiaSession(cb);

        if (cb != null) {
            cb.onSessionStarted(userId, session);
        }

        return session;
    }
}