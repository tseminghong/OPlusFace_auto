package android.hardware.biometrics.face;
//* FACE STUBS AUTO-GENERATED FILE. DO NOT MODIFY. */
import android.os.IInterface;
import android.os.RemoteException;
import android.hardware.keymaster.HardwareAuthToken;

public interface ISessionCallback extends IInterface {
    void onChallengeGenerated(long challenge) throws RemoteException;
    void onChallengeRevoked(long challenge) throws RemoteException;
    void onSessionStarted(int sessionId, ISession session) throws RemoteException;
    void onSessionClosed() throws RemoteException;
    void onEnrollmentProgress(int enrollmentId, int remaining) throws RemoteException;
    void onAuthenticationSucceeded(int enrollmentId, HardwareAuthToken hat) throws RemoteException;
    void onAuthenticationFailed() throws RemoteException;
    void onLockoutTimed(long durationMillis) throws RemoteException;
    void onLockoutPermanent() throws RemoteException;
    void onLockoutCleared() throws RemoteException;
    void onInteractionDetected() throws RemoteException;
    void onEnrollmentsEnumerated(int[] enrollmentIds) throws RemoteException;
    void onFeaturesRetrieved(byte[] features) throws RemoteException;
    void onFeatureSet(byte feature) throws RemoteException;
    void onAuthenticatorIdRetrieved(long authenticatorId) throws RemoteException;
    void onAuthenticatorIdInvalidated(long newAuthenticatorId) throws RemoteException;
    void onFeatureSet(int feature) throws RemoteException;
    void onEnrollmentsRemoved(int[] enrollmentIds) throws RemoteException;
    void onError(byte error, int vendorCode) throws RemoteException;
}