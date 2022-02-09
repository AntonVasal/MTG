package com.example.mtg.MainActivity.MainFragments.Home.TypeTaskFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mtg.R;

public class DecimalsFragment extends Fragment {

    TextView Add, Multi, Sub, Div;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type_task, container, false);
        Add = view.findViewById(R.id.add_text);
        Multi = view.findViewById(R.id.multi_text);
        Sub = view.findViewById(R.id.sub_text);
        Div = view.findViewById(R.id.div_text);
        initListeners();
        return view;
    }

    private void initListeners() {
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}