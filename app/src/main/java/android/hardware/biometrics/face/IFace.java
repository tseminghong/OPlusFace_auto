package android.hardware.biometrics.face;
//* FACE STUBS AUTO-GENERATED FILE. DO NOT MODIFY. */
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFace extends IInterface {
    public static abstract class Stub extends Binder implements IFace {
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }
        
        public static IFace asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IFace) return (IFace) iin;
            return null;
        }

        @Override
        public IBinder asBinder() { return this; }
        
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
        public static final String DESCRIPTOR = "android.hardware.biometrics.face.IFace";
    }

    int getInterfaceVersion() throws RemoteException;
    String getInterfaceHash() throws RemoteException;
    SensorProps[] getSensorProps() throws RemoteException;
    ISession createSession(int sensorId, int userId, ISessionCallback cb) throws RemoteException;
}