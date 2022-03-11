package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.models.UserResultsModel;
import com.example.mtg.R;

import java.util.ArrayList;
import java.util.List;

public class SubFragment extends Fragment {
    private Button natButton;
    private Button intButton;
    private Button decButton;

    private RecyclerView recyclerView;
    private ResultsRecyclerViewAdapter adapter;
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
                    "Victor",
                    "",
                    30000));
        }
        adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
        recyclerView.setAdapter(adapter);

    }
    private void initListeners() {
        natButton.setOnClickListener(view -> {
            natButton.setEnabled(false);
            natButton.setTextColor(getResources().getColor(R.color.blue,null));
            intButton.setEnabled(true);
            intButton.setTextColor(getResources().getColor(R.color.white,null));
            decButton.setEnabled(true);
            decButton.setTextColor(getResources().getColor(R.color.white,null));

            List<UserResultsModel> itemList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                itemList.add(new UserResultsModel(
                        "Volodymyr",
                        "",
                        18787));
            }
            adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
            recyclerView.setAdapter(adapter);

        });
        intButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            natButton.setTextColor(getResources().getColor(R.color.white,null));
            intButton.setEnabled(false);
            intButton.setTextColor(getResources().getColor(R.color.blue,null));
            decButton.setEnabled(true);
            decButton.setTextColor(getResources().getColor(R.color.white,null));

            List<UserResultsModel> itemList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                itemList.add(new UserResultsModel(
                        "Someone",
                        "",
                        14757));
            }
            adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
            recyclerView.setAdapter(adapter);
        });
        decButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            natButton.setTextColor(getResources().getColor(R.color.white,null));
            intButton.setEnabled(true);
            intButton.setTextColor(getResources().getColor(R.color.white,null));
            decButton.setEnabled(false);
            decButton.setTextColor(getResources().getColor(R.color.blue,null));

            List<UserResultsModel> itemList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                itemList.add(new UserResultsModel(
                        "Winner",
                        "",
                        23446));
            }
            adapter = new ResultsRecyclerViewAdapter(itemList,getContext());
            recyclerView.setAdapter(adapter);
        });
    }
}
