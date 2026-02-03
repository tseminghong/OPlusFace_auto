package android.hardware.biometrics.common;
//* FACE STUBS AUTO-GENERATED FILE. DO NOT MODIFY. */
import android.os.Parcel;
import android.os.Parcelable;
public class OperationContext implements Parcelable {
    public int id = 0;
    public byte reason = 0;
    public static final Creator<OperationContext> CREATOR = new Creator<OperationContext>() {
        @Override
        public OperationContext createFromParcel(Parcel in) { return new OperationContext(); }
        @Override
        public OperationContext[] newArray(int size) { return new OperationContext[size]; }
    };
    @Override
    public int describeContents() { return 0; }
    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}