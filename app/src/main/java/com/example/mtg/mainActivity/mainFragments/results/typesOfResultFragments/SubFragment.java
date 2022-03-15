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
import com.example.mtg.mainActivity.count.countModels.SubResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.SubViewModel;

import java.util.ArrayList;

public class SubFragment extends Fragment implements OnItemResultsRecyclerClickInterface {
    private Button natButton;
    private Button intButton;
    private Button decButton;

    private RecyclerView recyclerView;
    private ResultsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    ArrayList<SubResultsModel> subResultsNaturalsModels;
    ArrayList<SubResultsModel> subResultsIntegersModels;
    ArrayList<SubResultsModel> subResultsDecimalsModels;

    private SubViewModel subViewModel;
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

        subViewModel = new ViewModelProvider(requireActivity()).get(SubViewModel.class);


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
        subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
            if (subResultsModels != null && subResultsModels.size()!=0){
                subResultsModels.sort((subResultsModel, t1) -> t1.getSubNaturalScore() - subResultsModel.getSubNaturalScore());
                subResultsNaturalsModels = new ArrayList<>(subResultsModels);
                subResultsNaturalsModels.removeIf(subResultsModel -> subResultsModel.getSubNaturalScore()==0);
                adapter = new ResultsRecyclerViewAdapter(getContext(),3,1,this);
                adapter.setSubItemList(subResultsNaturalsModels);
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

            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null && subResultsModels.size()!=0){
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubIntegerScore() - subResultsModel.getSubIntegerScore());
                    subResultsIntegersModels = new ArrayList<>(subResultsModels);
                    subResultsIntegersModels.removeIf(subResultsModel -> subResultsModel.getSubIntegerScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),3,2,this);
                    adapter.setSubItemList(subResultsIntegersModels);
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

            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null && subResultsModels.size()!=0){
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubDecimalScore() - subResultsModel.getSubDecimalScore());
                    subResultsDecimalsModels = new ArrayList<>(subResultsModels);
                    subResultsDecimalsModels.removeIf(subResultsModel -> subResultsModel.getSubDecimalScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),3,3,this);
                    adapter.setSubItemList(subResultsDecimalsModels);
                    recyclerView.setAdapter(adapter);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {

    }
}
