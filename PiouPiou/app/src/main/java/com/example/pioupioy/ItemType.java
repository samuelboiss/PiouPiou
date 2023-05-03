package com.example.pioupioy;

public enum ItemType {
    CENSUS("census"), BIRDWATCHING("birdwatching"), OBSERVATION("observation");

    private final String label;

    ItemType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
