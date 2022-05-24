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
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Objects;

public class AddFragment extends BaseBindingFragment<FragmentResultsRecyclerBinding> implements OnItemResultsRecyclerClickInterface {

    private AddViewModel addViewModel;
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
    private int pos;
    private int number;
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
        binding.recyclerProgressBar.setVisibility(View.VISIBLE);
        binding.natButton.setEnabled(false);
        resultsDialog = new ResultsDialog(requireContext(), dialogBinding, name, nickname, imageUrl, country, score, tasks);
        observeStatus();
        generateItem();
        initListeners();
    }

    private void observeStatus() {
        resultsViewModel.getIsOnPause().observe(getViewLifecycleOwner(), isOnPause -> {
            Boolean isConnect = networkStateManager.getNetworkConnectivityStatus().getValue();
            if (counter != 0 && !isOnPause && isConnect != null && isConnect) {
                addViewModel.loadData();
            } else if (isOnPause) {
                addViewModel.removeCollectionListener();
            }
            counter++;
        });
    }

    private void generateItem() {
        addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
            if (userResultsModels != null) {
                if (Objects.requireNonNull(userResultsModels.data).size() != 0) {
                    sortNat(userResultsModels.data);
                    sortInt(userResultsModels.data);
                    sortDec(userResultsModels.data);
                    makeAdapter();
                    if (resultsDialog.isShowing()) {
                        loadOrUpdateDialog(pos,number);
                    }
                }
            }
        });
    }

    private void makeAdapter() {
        ResultsRecyclerViewAdapter adapter = new ResultsRecyclerViewAdapter(getContext(), this);
        if (!binding.natButton.isEnabled()) {
            adapter.setAddItemList(addResultsNaturalsModels, 1, 1);
        } else if (!binding.intButton.isEnabled()) {
            adapter.setAddItemList(addResultsIntegersModels, 2, 1);
        } else if (!binding.decButton.isEnabled()) {
            adapter.setAddItemList(addResultsDecimalsModels, 3, 1);
        }
        binding.resultRecycler.setAdapter(adapter);
        binding.recyclerProgressBar.setVisibility(View.GONE);
    }

    private void sortNat(ArrayList<AddResultsModel> addResultsModels) {
        mainListsSorter.setAddList(addResultsModels);
        addResultsNaturalsModels = mainListsSorter.sortAddNaturalModels();
    }

    private void sortInt(ArrayList<AddResultsModel> addResultsModels) {
        mainListsSorter.setAddList(addResultsModels);
        addResultsIntegersModels = mainListsSorter.sortAddIntegerModels();
    }

    private void sortDec(ArrayList<AddResultsModel> addResultsModels) {
        mainListsSorter.setAddList(addResultsModels);
        addResultsDecimalsModels = mainListsSorter.sortAddDecimalModels();
    }

    private void initListeners() {
        binding.natButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(false);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(true);
            makeAdapter();
        });
        binding.intButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(false);
            binding.decButton.setEnabled(true);
            makeAdapter();
        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            makeAdapter();
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        number = typeNumber;
        pos = position;
        loadOrUpdateDialog(position, typeNumber);
    }

    private void loadOrUpdateDialog(int position, int typeNumber) {
        switch (typeNumber) {
            case 1:
                natDialog(position);
                break;
            case 2:
                intDialog(position);
                break;
            case 3:
                decDialog(position);
                break;
        }
        loadDataFromFirestoreAndMakeDialogMethod();
    }

    private void natDialog(int position) {
        if (resultsDialog != null && resultsDialog.isShowing()) {
            for (int i = 0; i < addResultsNaturalsModels.size(); i++) {
                if (addResultsNaturalsModels.get(i).getId().equals(id)) {
                    loadDataNaturalMethod(i);
                }
            }
        } else {
            loadDataNaturalMethod(position);
            id = addResultsNaturalsModels.get(position).getId();
        }
    }

    private void intDialog(int position) {
        if (resultsDialog != null && resultsDialog.isShowing()) {
            for (int i = 0; i < addResultsIntegersModels.size(); i++) {
                if (addResultsIntegersModels.get(i).getId().equals(id)) {
                    loadDataNaturalMethod(i);
                }
            }
        } else {
            loadDataIntegerMethod(position);
            id = addResultsIntegersModels.get(position).getId();
        }
    }

    private void decDialog(int position) {
        if (resultsDialog != null && resultsDialog.isShowing()) {
            for (int i = 0; i < addResultsDecimalsModels.size(); i++) {
                if (addResultsDecimalsModels.get(i).getId().equals(id)) {
                    loadDataNaturalMethod(i);
                }
            }
        } else {
            loadDataDecimalMethod(position);
            id = addResultsDecimalsModels.get(position).getId();
        }
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
                        if (this.isVisible() && resultsDialog.isShowing()) {
                            requireActivity().runOnUiThread(() -> {
                                resultsDialog.loadData(name, nickname, imageUrl, country, score, tasks);
                                resultsDialog.setDataInViews();
                            });
                        } else if (this.isVisible() && !resultsDialog.isShowing()) {
                            dialogBinding = DialogBottomSheetResultsBinding.inflate(getLayoutInflater());
                            resultsDialog = new ResultsDialog(requireContext(), dialogBinding, name, nickname, imageUrl, country, score, tasks);
                            resultsDialog.show();
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