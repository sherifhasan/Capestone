package example.android.capestone.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import paperparcel.PaperParcel;

/**
 * Created by sheri on 2/6/2018.
 */
@PaperParcel
public class Source implements Parcelable {
    public static final Creator<Source> CREATOR = PaperParcelSource.CREATOR;

    @SerializedName("id")
     String id;
    @SerializedName("name")
     String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelSource.writeToParcel(this, parcel, i);
    }
}
