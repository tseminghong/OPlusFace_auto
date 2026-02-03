package android.hardware.biometrics.face;
//* FACE STUBS AUTO-GENERATED FILE. DO NOT MODIFY. */
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.common.fmq.MQDescriptor;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISession extends IInterface {
    public static abstract class Stub extends Binder implements ISession {
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }
        public static ISession asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof ISession) return (ISession) iin;
            return null; 
        }
        @Override
        public IBinder asBinder() { return this; }
        public static final String DESCRIPTOR = "android.hardware.biometrics.face.ISession";
    }

    int getInterfaceVersion() throws RemoteException;
    String getInterfaceHash() throws RemoteException;

    void generateChallenge() throws RemoteException;
    void revokeChallenge(long challenge) throws RemoteException;
    void getEnrollmentConfig(byte enrollmentType) throws RemoteException;
    ICancellationSignal enroll(HardwareAuthToken hat, byte type, byte[] features, MQDescriptor previewWindow) throws RemoteException;
    ICancellationSignal authenticate(long operationId) throws RemoteException;
    ICancellationSignal detectInteraction() throws RemoteException;
    void enumerateEnrollments() throws RemoteException;
    void removeEnrollments(int[] enrollmentIds) throws RemoteException;
    void getFeatures() throws RemoteException;
    void setFeature(int feature, boolean enabled, byte[] token) throws RemoteException;
    void getAuthenticatorId() throws RemoteException;
    void invalidateAuthenticatorId() throws RemoteException;
    void resetLockout(HardwareAuthToken hat) throws RemoteException;
    void close() throws RemoteException;
    
    ICancellationSignal enrollwithContext(HardwareAuthToken hat, byte type, byte[] features, MQDescriptor previewWindow, OperationContext context) throws RemoteException;
    ICancellationSignal authenticateWithContext(long operationId, OperationContext context) throws RemoteException;
    ICancellationSignal detectInteractionWithContext(OperationContext context) throws RemoteException;
    void onContextChanged(OperationContext context) throws RemoteException;
}