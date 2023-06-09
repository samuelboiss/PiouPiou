package com.example.pioupioy;

import android.os.Parcel;

import java.util.Arrays;

public enum BirdEventType {
    CENSUS("census"), BIRDWATCHING("birdwatching"), OBSERVATION("observation");

    private final String label;

    BirdEventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static BirdEventType getFromLabel(String label) {
        return Arrays.stream(BirdEventType.values()).filter(eventType -> eventType.label.equals(label)).findFirst().orElse(null);
    }

}
