package com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.addFragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.DialogBottomSheetResultsBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.models.countModels.AddResultsModel;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.resultsViewModel.ResultsViewModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.addFragment.addViewModel.AddViewModel;
import com.example.mtg.ui.dialogs.resultsDialog.ResultsDialog;
import com.example.mtg.utility.listsSorters.MainListsSorter;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class AddFragment extends BaseBindingFragment<FragmentResultsRecyclerBinding> implements OnItemResultsRecyclerClickInterface {

    private AddViewModel addViewModel;
    private ResultsRecyclerViewAdapter adapter;
    private ResultsDialog resultsDialog;
    private ResultsViewModel resultsViewModel;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<AddResultsModel> addResultsNaturalsModels;
    private ArrayList<AddResultsModel> addResultsIntegersModels;
    private ArrayList<AddResultsModel> addResultsDecimalsModels;
    private static final String USERS = "users";
    private MainListsSorter mainListsSorter;
    private String name;
    private NetworkStateManager networkStateManager;
    private String country;
    private String nickname;
    private String imageUrl;
    private String id;
    private int score;
    private int tasks;
    private DialogBottomSheetResultsBinding dialogBinding;
    private static int counter = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkStateManager = NetworkStateManager.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        addViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);

        resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);

        mainListsSorter = new MainListsSorter();
        resultsDialog = new ResultsDialog(requireContext(), null, "", "", "", "", 0, 0);

        binding.recyclerProgressBar.setVisibility(View.VISIBLE);
        binding.natButton.setEnabled(false);
        observeStatus();
        generateItem();
        initListeners();
    }

    private void observeStatus() {
        resultsViewModel.getIsOnPause().observe(getViewLifecycleOwner(), isOnPause -> {
            Boolean isConnect = networkStateManager.getNetworkConnectivityStatus().getValue();
            if (counter != 0 && !isOnPause && isConnect != null && isConnect) {
                addViewModel.loadData();
            }else if (isOnPause){
                addViewModel.removeCollectionListener();
            }
            counter++;
        });
    }

    private void generateItem() {
        addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
            if (userResultsModels != null) {
                if (Objects.requireNonNull(userResultsModels.data).size() != 0) {
                    mainListsSorter.setAddList(userResultsModels.data);
                    addResultsNaturalsModels = mainListsSorter.sortAddNaturalModels();
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 1, this);
                    adapter.setAddItemList(addResultsNaturalsModels);
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
            addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null) {
                    assert userResultsModels.data != null;
                    if (userResultsModels.data.size() != 0) {
                        mainListsSorter.setAddList(userResultsModels.data);
                        addResultsIntegersModels = mainListsSorter.sortAddIntegerModels();
                        adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 2, this);
                        adapter.setAddItemList(addResultsIntegersModels);
                        binding.resultRecycler.setAdapter(adapter);
                    }
                }
            });
        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null) {
                    if (Objects.requireNonNull(userResultsModels.data).size() != 0) {
                        mainListsSorter.setAddList(userResultsModels.data);
                        addResultsDecimalsModels = mainListsSorter.sortAddDecimalModels();
                        adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 3, this);
                        adapter.setAddItemList(addResultsDecimalsModels);
                        binding.resultRecycler.setAdapter(adapter);
                    }
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_results_recycler;
    }
}