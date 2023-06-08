package com.example.pioupioy;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.nio.charset.StandardCharsets;

public class BirdObservation extends BirdEvent implements Parcelable {
    private BirdEventType type;

    public BirdObservation(String name, int numberOfBird, String date, byte[] image, boolean huntable, GeoPoint address, String direction, String meteo) {
        super(name, numberOfBird, date, image, huntable, address, direction, meteo);
        this.type = BirdEventType.OBSERVATION;
    }

    @Override
    public String getLabel() {
        return this.type.getLabel();
    }

    public BirdEventType getType() {
        return this.type;
    }

    protected BirdObservation(Parcel in) throws IllegalAccessException {
        super(in.readString(), in.readInt(), in.readString(), in.createByteArray(), in.readByte() != 0, (GeoPoint) in.readParcelable(GeoPoint.class.getClassLoader()), in.readString(), in.readString());
        // decryt the type
        this.type = (BirdEventType) in.readSerializable();
    }

    public static final Creator<BirdObservation> CREATOR = new Creator<BirdObservation>() {
        @Override
        public BirdObservation createFromParcel(Parcel in) {
            try {
                return new BirdObservation(in);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public BirdObservation[] newArray(int size) {
            return new BirdObservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(numberOfBird);
        parcel.writeString(date);
        parcel.writeByteArray(image);
        parcel.writeByte((byte) (huntable ? 1 : 0));
        parcel.writeParcelable(address, i);
        parcel.writeString(direction);
        parcel.writeString(weather);
        parcel.writeSerializable(type);
    }

}
