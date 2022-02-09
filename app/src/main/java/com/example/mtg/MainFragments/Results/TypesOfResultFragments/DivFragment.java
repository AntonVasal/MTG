package com.example.mtg.MainFragments.Results.TypesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;

import com.example.mtg.R;

public class DivFragment extends Fragment {
    Button natButton;
    Button intButton;
    Button decButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results_recycler, container, false);
        natButton = view.findViewById(R.id.nat_button);
        intButton = view.findViewById(R.id.int_button);
        decButton = view.findViewById(R.id.dec_button);
        natButton.setEnabled(false);
        initListeners();
        return view;
    }
    private void initListeners() {
        natButton.setOnClickListener(view -> {
            natButton.setEnabled(false);
            intButton.setEnabled(true);
            decButton.setEnabled(true);
        });
        intButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            intButton.setEnabled(false);
            decButton.setEnabled(true);
        });
        decButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            intButton.setEnabled(true);
            decButton.setEnabled(false);
        });
    }
}
