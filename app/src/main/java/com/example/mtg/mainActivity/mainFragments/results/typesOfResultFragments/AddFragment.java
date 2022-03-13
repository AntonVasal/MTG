package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.AddNaturalViewModel;

public class AddFragment extends Fragment {

    private AddNaturalViewModel resultsViewModel;


    private ResultsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    private FragmentResultsRecyclerBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        layoutManager = new LinearLayoutManager(getContext());
        binding.resultRecycler.setLayoutManager(layoutManager);

        resultsViewModel = new ViewModelProvider(requireActivity()).get(AddNaturalViewModel.class);


        binding.natButton.setEnabled(false);
        initListeners();

        binding.recyclerProgressBar.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateItem();

    }

    private void generateItem() {
        resultsViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
            if (userResultsModels != null && userResultsModels.size() != 0) {
                adapter = new ResultsRecyclerViewAdapter(userResultsModels, getContext(),1,1);
                binding.resultRecycler.setAdapter(adapter);
                binding.recyclerProgressBar.setVisibility(View.GONE);
            } else {
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    adapter = new ResultsRecyclerViewAdapter(userResultsModels, getContext(),1,1);
                    binding.resultRecycler.setAdapter(adapter);
                    binding.recyclerProgressBar.setVisibility(View.GONE);
                }, 1000);
            }
        });
    }


    private void initListeners() {
        binding.natButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(false);
            binding.natButton.setTextColor(getResources().getColor(R.color.blue, null));
            binding.intButton.setEnabled(true);
            binding.intButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.decButton.setEnabled(true);
            binding.decButton.setTextColor(getResources().getColor(R.color.white, null));

            generateItem();
        });

        binding.intButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.natButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.intButton.setEnabled(false);
            binding.intButton.setTextColor(getResources().getColor(R.color.blue, null));
            binding.decButton.setEnabled(true);
            binding.decButton.setTextColor(getResources().getColor(R.color.white, null));

            resultsViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null && userResultsModels.size() != 0) {
                    adapter = new ResultsRecyclerViewAdapter(userResultsModels, getContext(),1,2);
                    binding.resultRecycler.setAdapter(adapter);
                    binding.recyclerProgressBar.setVisibility(View.GONE);
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        adapter = new ResultsRecyclerViewAdapter(userResultsModels, getContext(),1,2);
                        binding.resultRecycler.setAdapter(adapter);
                        binding.recyclerProgressBar.setVisibility(View.GONE);
                    }, 1000);
                }
            });
        });

        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.natButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.intButton.setEnabled(true);
            binding.intButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.decButton.setEnabled(false);
            binding.decButton.setTextColor(getResources().getColor(R.color.blue, null));

            resultsViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null && userResultsModels.size() != 0) {
                    adapter = new ResultsRecyclerViewAdapter(userResultsModels, getContext(),1,3);
                    binding.resultRecycler.setAdapter(adapter);
                    binding.recyclerProgressBar.setVisibility(View.GONE);
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        adapter = new ResultsRecyclerViewAdapter(userResultsModels, getContext(),1,3);
                        binding.resultRecycler.setAdapter(adapter);
                        binding.recyclerProgressBar.setVisibility(View.GONE);
                    }, 1000);
                }
            });
        });
    }
}