package com.example.pioupioy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BirdCensusInfo extends Fragment {
    private BirdCensus birdCensus;

    public BirdCensusInfo() {
        this.birdCensus = null;
    }

    public BirdCensusInfo(BirdCensus birdCensus) {
        this.birdCensus = birdCensus;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bird_census_info, container, false);

        if (birdCensus != null) {

            String numberOfBirdStr = "Nombre : " + birdCensus.getNumberOfBird();
            String isHuntableStr;
            ((ImageView) view.findViewById(R.id.bird_image)).setImageBitmap(birdCensus.getBitmapImage());
            ((TextView) view.findViewById(R.id.birdName)).setText(birdCensus.getName());
            ((TextView) view.findViewById(R.id.numberOfBird)).setText(numberOfBirdStr);
            if (birdCensus.isHuntable()) {
                isHuntableStr = "Chassable : oui";
                ((TextView) view.findViewById(R.id.huntable_area)).setText(isHuntableStr);
            } else {
                isHuntableStr = "Chassable : non";
                ((TextView) view.findViewById(R.id.huntable_area)).setText(isHuntableStr);
                ((TextView) view.findViewById(R.id.date)).setText(birdCensus.getDate());
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
