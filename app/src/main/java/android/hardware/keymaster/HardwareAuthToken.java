package android.hardware.keymaster;
//* FACE STUBS AUTO-GENERATED FILE. DO NOT MODIFY. */
import android.os.Parcel;
import android.os.Parcelable;
public class HardwareAuthToken implements Parcelable {
    public long challenge;
    public long userId;
    public long authenticatorId;
    public int authenticatorType;
    public long timestamp;
    public byte[] mac;
    public static final Creator<HardwareAuthToken> CREATOR = new Creator<HardwareAuthToken>() {
        @Override
        public HardwareAuthToken createFromParcel(Parcel in) { return new HardwareAuthToken(); }
        @Override
        public HardwareAuthToken[] newArray(int size) { return new HardwareAuthToken[size]; }
    };
    @Override
    public int describeContents() { return 0; }
    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}