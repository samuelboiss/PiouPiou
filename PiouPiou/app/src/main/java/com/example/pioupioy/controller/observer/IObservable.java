package com.example.pioupioy.controller.observer;

import com.example.pioupioy.controller.map.MarkerController;

import org.osmdroid.views.MapController;

public interface IObservable {
    void updateMarker(MarkerController markerController);
}
