package com.example.pioupioy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pioupioy.factory.Factory;

import java.util.List;

public class BirdEventDraftAdapter extends BaseAdapter {

    private Context context;
    private List<BirdEvent> eventList;

    private LayoutInflater layoutInflater;


    public BirdEventDraftAdapter(Context context, List<BirdEvent> eventList) {
        this.eventList = eventList;
        this.context = context;

        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.activity_draft_list, null);

        TextView date = (TextView) view.findViewById(R.id.date_draft);
        TextView type = (TextView) view.findViewById(R.id.type_draft);
        TextView species = (TextView) view.findViewById(R.id.species_draft);
        TextView numberOfBird = (TextView) view.findViewById(R.id.numberOfBird_draft);
        TextView direction = (TextView) view.findViewById(R.id.direction_draft);
        TextView weather = (TextView) view.findViewById(R.id.weather_draft);
        TextView huntable = (TextView) view.findViewById(R.id.huntable_draft);

        type.setText(eventList.get(i).getLabel());
        date.setText(eventList.get(i).getDate());
        species.setText(eventList.get(i).getName());
        numberOfBird.setText(String.valueOf(eventList.get(i).getNumberOfBird()));
        direction.setText(eventList.get(i).getDirection());
        weather.setText(eventList.get(i).getWeather());
        huntable.setText(eventList.get(i).isHuntable() ? "Oui" : "Non");


        GridLayout layout = view.findViewById(R.id.bird_draft);
        layout.setOnClickListener(view1 -> {
            Factory factory = new Factory();
            BirdEvent event;

            BirdEventType eventType = BirdEventType.getFromLabel(type.getText().toString());

            try {
                event = factory.build(
                        eventList.get(i).getName(),
                        eventType, eventList.get(i).getNumberOfBird(),
                        eventList.get(i).getDate(),
                        eventList.get(i).getImage(),
                        eventList.get(i).isHuntable(),
                        eventList.get(i).getAddress(),
                        eventList.get(i).getDirection(),
                        eventList.get(i).getWeather()
                );

            } catch (Throwable e) {
                // TODO : change this exception to make it more appropriate
                return;
            }

            // Create the Intent and add the String parameter
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("selectedBirdEvent", true);

            if (eventType.equals(BirdEventType.CENSUS)) {
                intent.putExtra("bird_event", (BirdCensus) event);
            } else if (eventType.equals(BirdEventType.OBSERVATION)) {
                intent.putExtra("bird_event", (BirdObservation) event);
            } else {
                return;
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Start the MainActivity with the Intent
            context.startActivity(intent);
        });

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        return view;
    }
}

