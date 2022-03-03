package com.example.mtg.MainActivity.MainFragments.Home.TypeTaskFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mtg.MainActivity.Count.CountFragment;
import com.example.mtg.R;

public class DecimalsFragment extends Fragment {

    private TextView Add, Multi, Sub, Div;


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
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Add.setOnClickListener(view -> {
            fragmentManager.beginTransaction().replace(R.id.main_app_container,new CountFragment(1,3))
                    .addToBackStack("")
                    .commit();

        });
        Multi.setOnClickListener(view -> {
            fragmentManager.beginTransaction().replace(R.id.main_app_container,new CountFragment(2,3))
                    .addToBackStack("")
                    .commit();
        });
        Sub.setOnClickListener(view -> {
            fragmentManager.beginTransaction().replace(R.id.main_app_container,new CountFragment(3,3))
                    .addToBackStack("")
                    .commit();
        });
        Div.setOnClickListener(view -> {
            fragmentManager.beginTransaction().replace(R.id.main_app_container,new CountFragment(4,3))
                    .addToBackStack("")
                    .commit();
        });
    }
}