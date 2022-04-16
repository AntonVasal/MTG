package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.R;
import com.example.mtg.databinding.BottomSheetResultsDialogBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.example.mtg.mainActivity.count.countModels.DivResultsModel;
import com.example.mtg.mainActivity.count.countModels.MultiResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.resultsDialog.ResultsDialog;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.MultiViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MultiFragment extends Fragment implements OnItemResultsRecyclerClickInterface {

    private static final String USERS = "users";
    private FragmentResultsRecyclerBinding binding;

    private ResultsRecyclerViewAdapter adapter;
    private ResultsDialog resultsDialog;

    private String name;
    private String country;
    private String nickname;
    private String imageUrl;
    private String id;
    private int score;
    private int tasks;

    private ArrayList<MultiResultsModel> multiResultsNaturalsModels;
    private ArrayList<MultiResultsModel> multiResultsIntegersModels;
    private ArrayList<MultiResultsModel> multiResultsDecimalsModels;

    private MultiViewModel multiViewModel;

    private FirebaseFirestore firebaseFirestore;
    private BottomSheetResultsDialogBinding dialogBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        multiViewModel = new ViewModelProvider(requireActivity()).get(MultiViewModel.class);
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.resultRecycler.setLayoutManager(layoutManager);
        generateItem();
        initListeners();
        binding.natButton.setEnabled(false);
    }

    private void generateItem() {
        multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
            if (multiResultsModels != null && multiResultsModels.size() != 0){
                sortNaturalsModels(multiResultsModels);
                adapter = new ResultsRecyclerViewAdapter(getContext(),2,1,this);
                adapter.setMultiItemList(multiResultsNaturalsModels);
                binding.resultRecycler.setAdapter(adapter);
            }
        });
    }
    private void initListeners() {
        binding.natButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(false);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(true);
            generateItem();
        });
        binding.intButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(false);
            binding.decButton.setEnabled(true);
            multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                if (multiResultsModels != null && multiResultsModels.size() != 0){
                    sortIntegersModels(multiResultsModels);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),2,2,this);
                    adapter.setMultiItemList(multiResultsIntegersModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });

        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                if (multiResultsModels != null && multiResultsModels.size() != 0){
                    sortDecimalsModels(multiResultsModels);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),2,3,this);
                    adapter.setMultiItemList(multiResultsDecimalsModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
            switch (typeNumber){
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
            }
        });
    }

    private void sortNaturalsModels(ArrayList<MultiResultsModel> multiResultsModels) {
        multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiNaturalScore() - multiResultsModel.getMultiNaturalScore());
        multiResultsNaturalsModels = new ArrayList<>(multiResultsModels);
        multiResultsNaturalsModels.removeIf(multiResultsModel -> multiResultsModel.getMultiNaturalScore()==0);
    }

    private void sortIntegersModels(ArrayList<MultiResultsModel> multiResultsModels){
        multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiIntegerScore() - multiResultsModel.getMultiIntegerScore());
        multiResultsIntegersModels = new ArrayList<>(multiResultsModels);
        multiResultsIntegersModels.removeIf(multiResultsModel -> multiResultsModel.getMultiIntegerScore()==0);
    }

    private void sortDecimalsModels(ArrayList<MultiResultsModel> multiResultsModels){
        multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiDecimalScore() - multiResultsModel.getMultiDecimalScore());
        multiResultsDecimalsModels = new ArrayList<>(multiResultsModels);
        multiResultsDecimalsModels.removeIf(multiResultsModel -> multiResultsModel.getMultiDecimalScore()==0);
    }

    private void loadDataNaturalMethod(int position) {
        score = multiResultsNaturalsModels.get(position).getMultiNaturalScore();
        tasks = multiResultsNaturalsModels.get(position).getMultiDecimalTasksAmount();
        imageUrl = multiResultsNaturalsModels.get(position).getImageUrl();
        nickname = multiResultsNaturalsModels.get(position).getNickname();
    }

    private void loadDataIntegerMethod(int position) {
        score = multiResultsIntegersModels.get(position).getMultiIntegerScore();
        tasks = multiResultsIntegersModels.get(position).getMultiIntegerTasksAmount();
        imageUrl = multiResultsIntegersModels.get(position).getImageUrl();
        nickname = multiResultsIntegersModels.get(position).getNickname();
    }

    private void loadDataDecimalMethod(int position) {
        score = multiResultsDecimalsModels.get(position).getMultiDecimalScore();
        tasks = multiResultsDecimalsModels.get(position).getMultiDecimalTasksAmount();
        imageUrl = multiResultsDecimalsModels.get(position).getImageUrl();
        nickname = multiResultsDecimalsModels.get(position).getNickname();
    }

    private void loadDataFromFirestoreAndMakeDialogMethod() {
        firebaseFirestore.collection(USERS).document(id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null && value.exists()) {
                        UserRegisterProfileModel userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                        assert userRegisterProfileModel != null;
                        name = userRegisterProfileModel.getName();
                        country = userRegisterProfileModel.getCountry();
                        if (resultsDialog == null || !resultsDialog.isShowing()) {
                            requireActivity().runOnUiThread(() -> {
                                dialogBinding = BottomSheetResultsDialogBinding.inflate(getLayoutInflater());
                                resultsDialog = new ResultsDialog(requireContext(), dialogBinding, name, nickname, imageUrl, country, score, tasks);
                                resultsDialog.show();
                            });
                        } else {
                            requireActivity().runOnUiThread(() -> {
                                resultsDialog.loadData(name, nickname, imageUrl, country, score, tasks);
                                resultsDialog.setDataInViews();
                            });
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
