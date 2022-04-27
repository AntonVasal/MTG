package com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mtg.databinding.DialogBottomSheetResultsBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.models.countModels.AddResultsModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.ui.dialogs.resultsDialog.ResultsDialog;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.viewModels.AddViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddFragment extends Fragment implements OnItemResultsRecyclerClickInterface {

    private AddViewModel addViewModel;

    private ResultsRecyclerViewAdapter adapter;
    private ResultsDialog resultsDialog;

    private FirebaseFirestore firebaseFirestore;

    private ArrayList<AddResultsModel> addResultsNaturalsModels;
    private ArrayList<AddResultsModel> addResultsIntegersModels;
    private ArrayList<AddResultsModel> addResultsDecimalsModels;

    private static final String USERS = "users";

    private String name;
    private String country;
    private String nickname;
    private String imageUrl;
    private String id;
    private int score;
    private int tasks;

    private FragmentResultsRecyclerBinding binding;
    private DialogBottomSheetResultsBinding dialogBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        View view = binding.getRoot();
        addViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerProgressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.resultRecycler.setLayoutManager(layoutManager);
        generateItem();
        initListeners();
        binding.natButton.setEnabled(false);
    }

    private void generateItem() {
        addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
            if (userResultsModels != null && userResultsModels.size() != 0) {
                sortNaturalModels(userResultsModels);
                adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 1, this);
                adapter.setAddItemList(addResultsNaturalsModels);
                binding.resultRecycler.setAdapter(adapter);
                binding.recyclerProgressBar.setVisibility(View.GONE);
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
            addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null && userResultsModels.size() != 0) {
                    sortIntegerModels(userResultsModels);
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 2, this);
                    adapter.setAddItemList(addResultsIntegersModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });
        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null && userResultsModels.size() != 0) {
                    sortDecimalModels(userResultsModels);
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 3, this);
                    adapter.setAddItemList(addResultsDecimalsModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), addResultsModels -> {
            switch (typeNumber) {
                case 1:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < addResultsNaturalsModels.size(); i++) {
                            if (addResultsNaturalsModels.get(i).getId().equals(id)) {
                                loadDataNaturalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataNaturalMethod(position);
                        id = addResultsNaturalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 2:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < addResultsIntegersModels.size(); i++) {
                            if (addResultsIntegersModels.get(i).getId().equals(id)) {
                                loadDataNaturalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataIntegerMethod(position);
                        id = addResultsIntegersModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 3:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < addResultsDecimalsModels.size(); i++) {
                            if (addResultsDecimalsModels.get(i).getId().equals(id)) {
                                loadDataNaturalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataDecimalMethod(position);
                        id = addResultsDecimalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
            }
        });
    }

    private void sortNaturalModels(ArrayList<AddResultsModel> userResultsModels) {
        userResultsModels.sort((addResultsModel, t1) -> t1.getAddNaturalScore() - addResultsModel.getAddNaturalScore());
        addResultsNaturalsModels = new ArrayList<>(userResultsModels);
        addResultsNaturalsModels.removeIf(addResultsModel -> addResultsModel.getAddNaturalScore() == 0);
    }

    private void sortIntegerModels(ArrayList<AddResultsModel> userResultsModels) {
        userResultsModels.sort((addResultsModel, t1) -> t1.getAddIntegerScore() - addResultsModel.getAddIntegerScore());
        addResultsIntegersModels = new ArrayList<>(userResultsModels);
        addResultsIntegersModels.removeIf(addResultsModel -> addResultsModel.getAddIntegerScore() == 0);
    }

    private void sortDecimalModels(ArrayList<AddResultsModel> userResultsModels) {
        userResultsModels.sort((addResultsModel, t1) -> t1.getAddDecimalScore() - addResultsModel.getAddDecimalScore());
        addResultsDecimalsModels = new ArrayList<>(userResultsModels);
        addResultsDecimalsModels.removeIf(addResultsModel -> addResultsModel.getAddDecimalScore() == 0);
    }

    private void loadDataNaturalMethod(int position) {
        score = addResultsNaturalsModels.get(position).getAddNaturalScore();
        tasks = addResultsNaturalsModels.get(position).getAddNaturalTasksAmount();
        imageUrl = addResultsNaturalsModels.get(position).getImageUrl();
        nickname = addResultsNaturalsModels.get(position).getNickname();
    }

    private void loadDataIntegerMethod(int position) {
        score = addResultsIntegersModels.get(position).getAddIntegerScore();
        tasks = addResultsIntegersModels.get(position).getAddIntegerTasksAmount();
        imageUrl = addResultsIntegersModels.get(position).getImageUrl();
        nickname = addResultsIntegersModels.get(position).getNickname();
    }

    private void loadDataDecimalMethod(int position) {
        score = addResultsDecimalsModels.get(position).getAddDecimalScore();
        tasks = addResultsDecimalsModels.get(position).getAddDecimalTasksAmount();
        imageUrl = addResultsDecimalsModels.get(position).getImageUrl();
        nickname = addResultsDecimalsModels.get(position).getNickname();
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
                        if ((resultsDialog == null || !resultsDialog.isShowing()) && this.isVisible()) {
                            requireActivity().runOnUiThread(() -> {
                                dialogBinding = DialogBottomSheetResultsBinding.inflate(getLayoutInflater());
                                resultsDialog = new ResultsDialog(requireContext(), dialogBinding, name, nickname, imageUrl, country, score, tasks);
                                resultsDialog.show();
                            });
                        } else if (this.isVisible()) {
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
        dialogBinding = null;
    }

}