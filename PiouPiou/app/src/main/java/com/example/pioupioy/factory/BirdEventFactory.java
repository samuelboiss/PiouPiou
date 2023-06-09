package com.example.pioupioy.factory;

import com.example.pioupioy.BirdEvent;
import com.example.pioupioy.BirdEventType;

import org.osmdroid.util.GeoPoint;

public abstract class BirdEventFactory {
    protected abstract BirdEvent build(String name, BirdEventType type, int numberOfBird, String date, byte[] image, boolean huntable, GeoPoint address, String direction, String meteo)  throws Throwable;

}
