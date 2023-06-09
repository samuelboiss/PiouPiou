package com.example.pioupioy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BirdEventInfo extends Fragment {
    private BirdEvent birdEvent;

    public BirdEventInfo() {
        this.birdEvent = null;
    }

    public BirdEventInfo(BirdEvent birdEvent) {
        this.birdEvent = birdEvent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bird_census_info, container, false);

        if (birdEvent != null) {

            String numberOfBirdStr = "Nombre : " + birdEvent.getNumberOfBird();
            String isHuntableStr;
            ((ImageView) view.findViewById(R.id.bird_image)).setImageBitmap(birdEvent.getBitmapImage());
            ((TextView) view.findViewById(R.id.birdName)).setText(birdEvent.getName());
            ((TextView) view.findViewById(R.id.numberOfBird)).setText(numberOfBirdStr);
            if (birdEvent.isHuntable()) {
                isHuntableStr = "Chassable : oui";
                ((TextView) view.findViewById(R.id.huntable_area)).setText(isHuntableStr);
            } else {
                isHuntableStr = "Chassable : non";
                ((TextView) view.findViewById(R.id.huntable_area)).setText(isHuntableStr);
                ((TextView) view.findViewById(R.id.date)).setText(birdEvent.getDate());
            }
        }
        Button closeButton = view.findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
