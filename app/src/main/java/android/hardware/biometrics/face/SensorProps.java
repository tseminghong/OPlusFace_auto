package android.hardware.biometrics.face;
//* FACE STUBS AUTO-GENERATED FILE. DO NOT MODIFY. */
import android.os.Parcel;
import android.os.Parcelable;
public class SensorProps implements Parcelable {
    public static final Creator<SensorProps> CREATOR = new Creator<SensorProps>() {
        @Override
        public SensorProps createFromParcel(Parcel in) { return new SensorProps(); }
        @Override
        public SensorProps[] newArray(int size) { return new SensorProps[size]; }
    };
    @Override
    public int describeContents() { return 0; }
    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}