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

import com.example.mtg.core.listsSorters.MainListsSorter;
import com.example.mtg.databinding.DialogBottomSheetResultsBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.models.countModels.SubResultsModel;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.viewModels.SubViewModel;
import com.example.mtg.ui.dialogs.resultsDialog.ResultsDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SubFragment extends Fragment implements OnItemResultsRecyclerClickInterface {

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
    private MainListsSorter mainListsSorter;
    private ArrayList<SubResultsModel> subResultsNaturalsModels;
    private ArrayList<SubResultsModel> subResultsIntegersModels;
    private ArrayList<SubResultsModel> subResultsDecimalsModels;

    private SubViewModel subViewModel;

    private FirebaseFirestore firebaseFirestore;
    private DialogBottomSheetResultsBinding dialogBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subViewModel = new ViewModelProvider(requireActivity()).get(SubViewModel.class);
        mainListsSorter = new MainListsSorter();
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.recyclerProgressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.resultRecycler.setLayoutManager(layoutManager);
        generateItem();
        binding.natButton.setEnabled(false);
        initListeners();
    }

    private void generateItem() {
        subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
            if (subResultsModels != null) {
                assert subResultsModels.data != null;
                if (subResultsModels.data.size() != 0) {
                    mainListsSorter.setSubList(subResultsModels.data);
                    subResultsNaturalsModels = mainListsSorter.sortSubNaturalsModels();
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 3, 1, this);
                    adapter.setSubItemList(subResultsNaturalsModels);
                    binding.resultRecycler.setAdapter(adapter);
                    binding.recyclerProgressBar.setVisibility(View.GONE);
                }
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
            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null) {
                    assert subResultsModels.data != null;
                    if (subResultsModels.data.size() != 0) {
                        mainListsSorter.setSubList(subResultsModels.data);
                        subResultsIntegersModels = mainListsSorter.sortSubIntegersModels();
                        adapter = new ResultsRecyclerViewAdapter(getContext(), 3, 2, this);
                        adapter.setSubItemList(subResultsIntegersModels);
                        binding.resultRecycler.setAdapter(adapter);
                    }
                }
            });
        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null) {
                    assert subResultsModels.data != null;
                    if (subResultsModels.data.size() != 0) {
                        mainListsSorter.setSubList(subResultsModels.data);
                        subResultsDecimalsModels = mainListsSorter.sortSubDecimalsModels();
                        adapter = new ResultsRecyclerViewAdapter(getContext(), 3, 3, this);
                        adapter.setSubItemList(subResultsDecimalsModels);
                        binding.resultRecycler.setAdapter(adapter);
                    }
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
            switch (typeNumber) {
                case 1:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < subResultsNaturalsModels.size(); i++) {
                            if (subResultsNaturalsModels.get(i).getId().equals(id)) {
                                loadDataNaturalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataNaturalMethod(position);
                        id = subResultsNaturalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 2:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < subResultsIntegersModels.size(); i++) {
                            if (subResultsIntegersModels.get(i).getId().equals(id)) {
                                loadDataIntegerMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataIntegerMethod(position);
                        id = subResultsIntegersModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 3:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < subResultsDecimalsModels.size(); i++) {
                            if (subResultsDecimalsModels.get(i).getId().equals(id)) {
                                loadDataDecimalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataDecimalMethod(position);
                        id = subResultsDecimalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
            }
        });
    }

    private void loadDataNaturalMethod(int position) {
        score = subResultsNaturalsModels.get(position).getSubNaturalScore();
        tasks = subResultsNaturalsModels.get(position).getSubNaturalTasksAmount();
        imageUrl = subResultsNaturalsModels.get(position).getImageUrl();
        nickname = subResultsNaturalsModels.get(position).getNickname();
    }

    private void loadDataIntegerMethod(int position) {
        score = subResultsIntegersModels.get(position).getSubIntegerScore();
        tasks = subResultsIntegersModels.get(position).getSubIntegerTasksAmount();
        imageUrl = subResultsIntegersModels.get(position).getImageUrl();
        nickname = subResultsIntegersModels.get(position).getNickname();
    }

    private void loadDataDecimalMethod(int position) {
        score = subResultsDecimalsModels.get(position).getSubDecimalScore();
        tasks = subResultsDecimalsModels.get(position).getSubDecimalTasksAmount();
        imageUrl = subResultsDecimalsModels.get(position).getImageUrl();
        nickname = subResultsDecimalsModels.get(position).getNickname();
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
    }
}
