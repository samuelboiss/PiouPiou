package com.example.pioupioy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BirdCensus {
    private final String name;
    private final int numberOfBird;
    private final boolean huntable;
    private final Date date;
    private final ItemType type;
    private final byte[] image;

    private String meteo;
    private String direction;


    public BirdCensus(String name, int numberOfBird, Date date, ItemType type, byte[] image, boolean huntable, String meteo, String direction) {
        this.name = name;
        this.numberOfBird = numberOfBird;
        this.date = date;
        this.type = type;
        this.image = image;
        this.huntable = huntable;
        this.meteo = meteo;
        this.direction = direction;
    }


    public String getName() {
        return name;
    }

    public boolean isHuntable() {
        return huntable;
    }

    public byte[] getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfBird() {
        return numberOfBird;
    }

    public ItemType getType() {
        return type;
    }

    public Bitmap getBitmapImage() {
        // Convert byte array to Bitmap
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public String getFormattedTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public String getMeteo() {
        return meteo;
    }

    public String getDirection() {
        return direction;
    }
}
