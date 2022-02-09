package com.example.mtg.MainFragments.Results.TypesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtg.R;
import com.example.mtg.MainFragments.Results.Adapters.ResultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.MainFragments.Results.Models.UserResultsModel;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {
    Button natButton;
    Button intButton;
    Button decButton;


    RecyclerView recyclerView;
    ResultsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;


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

        recyclerView = view.findViewById(R.id.result_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        generateItem();

        return view;
    }

    private void generateItem() {
        List<UserResultsModel> itemList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            itemList.add(new UserResultsModel(
                    "Anton ",
                    "",
                    "10000"));
        }
        adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
        recyclerView.setAdapter(adapter);

    }



    private void initListeners() {
        natButton.setOnClickListener(view -> {
            natButton.setEnabled(false);
            intButton.setEnabled(true);
            decButton.setEnabled(true);

            List<UserResultsModel> itemList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                itemList.add(new UserResultsModel(
                        "Anton ",
                        "",
                        "10000"));
            }
            adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
            recyclerView.setAdapter(adapter);
        });

        intButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            intButton.setEnabled(false);
            decButton.setEnabled(true);

            List<UserResultsModel> itemList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                itemList.add(new UserResultsModel(
                        "Mr. Man ",
                        "",
                        "31212"));
            }
            adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
            recyclerView.setAdapter(adapter);
        });

        decButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            intButton.setEnabled(true);
            decButton.setEnabled(false);

            List<UserResultsModel> itemList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                itemList.add(new UserResultsModel(
                        "Antonio ",
                        "",
                        "24664"));
            }
            adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
            recyclerView.setAdapter(adapter);
        });
    }
}