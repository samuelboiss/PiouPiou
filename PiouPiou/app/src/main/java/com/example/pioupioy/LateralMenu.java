package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LateralMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LateralMenu extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LateralMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LateralMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static LateralMenu newInstance(String param1, String param2) {
        LateralMenu fragment = new LateralMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lateral_menu, container, false);

        Button add_button = view.findViewById(R.id.button_add);
        add_button.setOnClickListener(this);

        Button draft_button = view.findViewById(R.id.button_draft);
        draft_button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.button_add) {
            Intent intent = new Intent(getActivity(), Ajouter.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.button_draft) {
            Intent intent = new Intent(getActivity(), DraftActivity.class);
            startActivity(intent);
        }
    }
}