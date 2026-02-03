package android.hardware.common.fmq;
//* FACE STUBS AUTO-GENERATED FILE. DO NOT MODIFY. */
import android.os.Parcel;
import android.os.Parcelable;
public class MQDescriptor implements Parcelable {
    public static final Creator<MQDescriptor> CREATOR = new Creator<MQDescriptor>() {
        @Override
        public MQDescriptor createFromParcel(Parcel in) { return new MQDescriptor(); }
        @Override
        public MQDescriptor[] newArray(int size) { return new MQDescriptor[size]; }
    };
    @Override
    public int describeContents() { return 0; }
    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}