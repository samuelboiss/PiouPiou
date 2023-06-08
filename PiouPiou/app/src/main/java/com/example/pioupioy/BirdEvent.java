package com.example.pioupioy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;

import com.example.pioupioy.utils.ImageUtils;

import org.osmdroid.util.GeoPoint;

import java.nio.ByteBuffer;

public abstract class BirdEvent implements Parcelable {
    protected String name;
    protected int numberOfBird;
    protected boolean huntable;
    protected byte[] image;
    protected String date;
    protected GeoPoint address;

    protected String direction;

    protected String weather;

    public BirdEvent(String name, int numberOfBird, String date, byte[] image, boolean huntable, GeoPoint address, String direction, String weather) {
        this.name = name;
        this.numberOfBird = numberOfBird;
        this.date = date;
        this.image = image;
        this.huntable = huntable;
        this.address = address;
        this.direction = direction;
        this.weather = weather;
    }

    public String getName() {
        return this.name;
    }

    public int getNumberOfBird() {
        return this.numberOfBird;
    }

    public boolean isHuntable() {
        return this.huntable;
    }

    public byte[] getImage() {
        return this.image;
    }

    public String getDirection() {
        return this.direction;
    }

    public String getWeather() {
        return this.weather;
    }

    public Bitmap getBitmapImage() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public int getImageAsInt() {
        return image != null ? ByteBuffer.wrap(image).getInt() : R.drawable.bird_default_icon;
    }

    public GeoPoint getAddress() {
        return this.address;
    }

    public String getDate() {
        return this.date;
    }


    public String getStringImage() {
        byte[] img = getImage();
        return ImageUtils.convertFromByteArray(img);
    }

    public void setStringImage(String image) {
        this.image = ImageUtils.convert(image);
    }

    public Double getLatitude() {
        return this.address.getLatitude();
    }

    public Double getLongitude() {
        return this.address.getLongitude();
    }

    public void setLatitude(Double latitude) {
        this.address.setLatitude(latitude);
    }

    public void setLongitude(Double longitude) {
        this.address.setLongitude(longitude);
    }

    public abstract String getLabel();

}
