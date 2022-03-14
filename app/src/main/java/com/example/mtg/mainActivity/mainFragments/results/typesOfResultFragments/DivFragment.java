package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtg.R;
import com.example.mtg.mainActivity.count.countModels.DivResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.DivViewModel;

import java.util.ArrayList;

public class DivFragment extends Fragment {
    private Button natButton;
    private Button intButton;
    private Button decButton;

    private RecyclerView recyclerView;
    private ResultsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    ArrayList<DivResultsModel> divResultsNaturalsModels;
    ArrayList<DivResultsModel> divResultsIntegersModels;
    ArrayList<DivResultsModel> divResultsDecimalsModels;

    private DivViewModel divViewModel;


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

        divViewModel = new ViewModelProvider(requireActivity()).get(DivViewModel.class);



        recyclerView = view.findViewById(R.id.result_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateItem();
    }

    private void generateItem() {
        divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
            if (divResultsModels!=null && divResultsModels.size()!=0){
                divResultsModels.sort((divResultsModel, t1) -> t1.getDivNaturalScore() - divResultsModel.getDivNaturalScore());
                divResultsNaturalsModels = new ArrayList<>(divResultsModels);
                divResultsNaturalsModels.removeIf(divResultsModel -> divResultsModel.getDivNaturalScore()==0);
                adapter = new ResultsRecyclerViewAdapter(getContext(),4,1);
                adapter.setDivItemList(divResultsNaturalsModels);
                recyclerView.setAdapter(adapter);
            }
        });
    }
    private void initListeners() {
        natButton.setOnClickListener(view -> {
            natButton.setEnabled(false);
            natButton.setTextColor(getResources().getColor(R.color.blue,null));
            intButton.setEnabled(true);
            intButton.setTextColor(getResources().getColor(R.color.white,null));
            decButton.setEnabled(true);
            decButton.setTextColor(getResources().getColor(R.color.white,null));

            generateItem();
        });
        intButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            natButton.setTextColor(getResources().getColor(R.color.white,null));
            intButton.setEnabled(false);
            intButton.setTextColor(getResources().getColor(R.color.blue,null));
            decButton.setEnabled(true);
            decButton.setTextColor(getResources().getColor(R.color.white,null));

            divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                if (divResultsModels!=null && divResultsModels.size()!=0){
                    divResultsModels.sort((divResultsModel, t1) -> t1.getDivIntegerScore() - divResultsModel.getDivIntegerScore());
                    divResultsIntegersModels = new ArrayList<>(divResultsModels);
                    divResultsIntegersModels.removeIf(divResultsModel -> divResultsModel.getDivIntegerScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),4,2);
                    adapter.setDivItemList(divResultsIntegersModels);
                    recyclerView.setAdapter(adapter);
                }
            });
        });
        decButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            natButton.setTextColor(getResources().getColor(R.color.white,null));
            intButton.setEnabled(true);
            intButton.setTextColor(getResources().getColor(R.color.white,null));
            decButton.setEnabled(false);
            decButton.setTextColor(getResources().getColor(R.color.blue,null));

            divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                if (divResultsModels!=null && divResultsModels.size()!=0){
                    divResultsModels.sort((divResultsModel, t1) -> t1.getDivDecimalScore() - divResultsModel.getDivDecimalScore());
                    divResultsDecimalsModels = new ArrayList<>(divResultsModels);
                    divResultsDecimalsModels.removeIf(divResultsModel -> divResultsModel.getDivDecimalScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),4,3);
                    adapter.setDivItemList(divResultsDecimalsModels);
                    recyclerView.setAdapter(adapter);
                }
            });
        });
    }
}
