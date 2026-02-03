package ax.nd.faceunlock;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.face.EnrollmentType;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.common.fmq.MQDescriptor;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.RemoteException;
import android.util.Log;

public class RianixiaSession extends ISession.Stub {
    private static final String TAG = "RianixiaSession";
    private final ISessionCallback mCb;

    public RianixiaSession(ISessionCallback cb) {
        this.mCb = cb;
    }

    @Override
    public void getEnrollmentConfig(byte enrollmentType) throws RemoteException {
        android.util.Log.d("RianixiaSession", "getEnrollmentConfig called with type: " + enrollmentType);
    }

    @Override
    public int getInterfaceVersion() {
        return 2;
    }

    @Override
    public String getInterfaceHash() {
        return "rianixia_hash";
    }


    @Override
    public void generateChallenge() throws RemoteException {
        Log.d(TAG, "generateChallenge");
        if (mCb != null) {
            mCb.onChallengeGenerated(0L);
        }
    }

    @Override
    public void revokeChallenge(long challenge) throws RemoteException {
        Log.d(TAG, "revokeChallenge");
        if (mCb != null) {
            mCb.onChallengeRevoked(challenge);
        }
    }

    @Override
    public void getAuthenticatorId() throws RemoteException {
        Log.d(TAG, "getAuthenticatorId");
        if (mCb != null) {
            mCb.onAuthenticatorIdRetrieved(0L);
        }
    }

    @Override
    public void invalidateAuthenticatorId() throws RemoteException {
        Log.d(TAG, "invalidateAuthenticatorId");
        if (mCb != null) {
            mCb.onAuthenticatorIdInvalidated(0L);
        }
    }

    @Override
    public void enumerateEnrollments() throws RemoteException {
        Log.d(TAG, "enumerateEnrollments");
        if (mCb != null) {
            mCb.onEnrollmentsEnumerated(new int[0]);
        }
    }

    @Override
    public void removeEnrollments(int[] enrollmentIds) throws RemoteException {
        Log.d(TAG, "removeEnrollments");
        if (mCb != null) {
            mCb.onEnrollmentsRemoved(enrollmentIds);
        }
    }

    @Override
    public void getFeatures() throws RemoteException {
        Log.d(TAG, "getFeatures");
        if (mCb != null) {
            mCb.onFeaturesRetrieved(new byte[0]);
        }
    }

    @Override
    public void setFeature(int feature, boolean enabled, byte[] token) throws RemoteException {
        Log.d(TAG, "setFeature");
        if (mCb != null) {
            mCb.onFeatureSet(feature);
        }
    }

    @Override
    public void resetLockout(HardwareAuthToken hat) throws RemoteException {
        Log.d(TAG, "resetLockout");
        if (mCb != null) {
            mCb.onLockoutCleared();
        }
    }

    @Override
    public void close() throws RemoteException {
        Log.d(TAG, "close");
        if (mCb != null) {
            mCb.onSessionClosed();
        }
    }


    @Override
    public ICancellationSignal authenticate(long operationId) throws RemoteException {
        return null;
    }

    @Override
    public ICancellationSignal enroll(HardwareAuthToken hat, byte type, byte[] features, MQDescriptor previewWindow) throws RemoteException {
        return null;
    }

    @Override
    public ICancellationSignal detectInteraction() throws RemoteException {
        return null;
    }

    @Override
    public ICancellationSignal enrollwithContext(HardwareAuthToken hat, byte type, byte[] features, MQDescriptor previewWindow, OperationContext context) throws RemoteException {
        return null;
    }

    @Override
    public ICancellationSignal authenticateWithContext(long operationId, OperationContext context) throws RemoteException {
        return null;
    }
    
    @Override
    public ICancellationSignal detectInteractionWithContext(OperationContext context) throws RemoteException {
        return null;
    }

    @Override
    public void onContextChanged(OperationContext context) throws RemoteException {
    }
}