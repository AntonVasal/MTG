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
import com.example.mtg.models.countModels.DivResultsModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.ui.dialogs.resultsDialog.ResultsDialog;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.viewModels.DivViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DivFragment extends Fragment implements OnItemResultsRecyclerClickInterface {

    private static final String USERS = "users";
    private FragmentResultsRecyclerBinding binding;

    private ResultsRecyclerViewAdapter adapter;
    private ResultsDialog resultsDialog;

    private ArrayList<DivResultsModel> divResultsNaturalsModels;
    private ArrayList<DivResultsModel> divResultsIntegersModels;
    private ArrayList<DivResultsModel> divResultsDecimalsModels;

    private DivViewModel divViewModel;

    private FirebaseFirestore firebaseFirestore;
    private DialogBottomSheetResultsBinding dialogBinding;

    private String name;
    private String country;
    private String nickname;
    private String imageUrl;
    private String id;
    private int score;
    private int tasks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        divViewModel = new ViewModelProvider(requireActivity()).get(DivViewModel.class);
        firebaseFirestore = FirebaseFirestore.getInstance();
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
        divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
            if (divResultsModels != null && divResultsModels.size() != 0) {
                sortNaturalsModels(divResultsModels);
                adapter = new ResultsRecyclerViewAdapter(getContext(), 4, 1, this);
                adapter.setDivItemList(divResultsNaturalsModels);
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
            divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                if (divResultsModels != null && divResultsModels.size() != 0) {
                    sortIntegersModels(divResultsModels);
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 4, 2, this);
                    adapter.setDivItemList(divResultsIntegersModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });
        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                if (divResultsModels != null && divResultsModels.size() != 0) {
                    sortDecimalsModels(divResultsModels);
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 4, 3, this);
                    adapter.setDivItemList(divResultsDecimalsModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
            switch (typeNumber) {
                case 1:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < divResultsNaturalsModels.size(); i++) {
                            if (divResultsNaturalsModels.get(i).getId().equals(id)) {
                                loadDataNaturalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataNaturalMethod(position);
                        id = divResultsNaturalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 2:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < divResultsIntegersModels.size(); i++) {
                            if (divResultsIntegersModels.get(i).getId().equals(id)) {
                                loadDataIntegerMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataIntegerMethod(position);
                        id = divResultsIntegersModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 3:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < divResultsDecimalsModels.size(); i++) {
                            if (divResultsDecimalsModels.get(i).getId().equals(id)) {
                                loadDataDecimalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataDecimalMethod(position);
                        id = divResultsDecimalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
            }
        });
    }

    private void sortNaturalsModels(ArrayList<DivResultsModel> divResultsModels) {
        divResultsModels.sort((divResultsModel, t1) -> t1.getDivNaturalScore() - divResultsModel.getDivNaturalScore());
        divResultsNaturalsModels = new ArrayList<>(divResultsModels);
        divResultsNaturalsModels.removeIf(divResultsModel -> divResultsModel.getDivNaturalScore() == 0);
    }

    private void sortIntegersModels(ArrayList<DivResultsModel> divResultsModels) {
        divResultsModels.sort((divResultsModel, t1) -> t1.getDivIntegerScore() - divResultsModel.getDivIntegerScore());
        divResultsIntegersModels = new ArrayList<>(divResultsModels);
        divResultsIntegersModels.removeIf(divResultsModel -> divResultsModel.getDivIntegerScore() == 0);
    }

    private void sortDecimalsModels(ArrayList<DivResultsModel> divResultsModels) {
        divResultsModels.sort((divResultsModel, t1) -> t1.getDivDecimalScore() - divResultsModel.getDivDecimalScore());
        divResultsDecimalsModels = new ArrayList<>(divResultsModels);
        divResultsDecimalsModels.removeIf(divResultsModel -> divResultsModel.getDivDecimalScore()==0);
    }

    private void loadDataNaturalMethod(int position) {
        score = divResultsNaturalsModels.get(position).getDivNaturalScore();
        tasks = divResultsNaturalsModels.get(position).getDivNaturalTasksAmount();
        imageUrl = divResultsNaturalsModels.get(position).getImageUrl();
        nickname = divResultsNaturalsModels.get(position).getNickname();
    }

    private void loadDataIntegerMethod(int position) {
        score = divResultsIntegersModels.get(position).getDivIntegerScore();
        tasks = divResultsIntegersModels.get(position).getDivIntegerTasksAmount();
        imageUrl = divResultsIntegersModels.get(position).getImageUrl();
        nickname = divResultsIntegersModels.get(position).getNickname();
    }

    private void loadDataDecimalMethod(int position) {
        score = divResultsDecimalsModels.get(position).getDivDecimalScore();
        tasks = divResultsDecimalsModels.get(position).getDivDecimalTasksAmount();
        imageUrl = divResultsDecimalsModels.get(position).getImageUrl();
        nickname = divResultsDecimalsModels.get(position).getNickname();
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
