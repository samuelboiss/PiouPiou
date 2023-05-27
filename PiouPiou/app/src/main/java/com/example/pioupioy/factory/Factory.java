package com.example.pioupioy.factory;

import com.example.pioupioy.BirdCensus;
import com.example.pioupioy.BirdEvent;
import com.example.pioupioy.BirdEventType;
import com.example.pioupioy.BirdObservation;

import org.osmdroid.util.GeoPoint;

public class Factory extends BirdEventFactory {

    @Override
    public BirdEvent build(String name, BirdEventType type, int numberOfBird, String date, byte[] image, boolean huntable, GeoPoint address, String direction, String meteo) throws Throwable {
        if (type.equals(BirdEventType.CENSUS)) {
            return new BirdCensus(name, numberOfBird, date, image, huntable, address, direction, meteo);
        }
        if (type.equals(BirdEventType.OBSERVATION)) {
            return new BirdObservation(name, numberOfBird, date, image, huntable, address, direction, meteo);
        }
        throw new Throwable("event type not made");
    }

}
