package com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.subFragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.DialogBottomSheetResultsBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.models.countModels.SubResultsModel;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.resultsViewModel.ResultsViewModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.subFragment.subViewModel.SubViewModel;
import com.example.mtg.ui.dialogs.resultsDialog.ResultsDialog;
import com.example.mtg.utility.listsSorters.MainListsSorter;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

public class SubFragment extends BaseBindingFragment<FragmentResultsRecyclerBinding> implements OnItemResultsRecyclerClickInterface {

    private static final String USERS = "users";
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
    private static int counter = 0;
    private NetworkStateManager networkStateManager;
    private SubViewModel subViewModel;
    private ResultsViewModel resultsViewModel;
    private FirebaseFirestore firebaseFirestore;
    private DialogBottomSheetResultsBinding dialogBinding;
    private int pos;
    private int number;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkStateManager = NetworkStateManager.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        subViewModel = new ViewModelProvider(requireActivity()).get(SubViewModel.class);
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
                subViewModel.loadData();
            } else if (isOnPause) {
                subViewModel.removeCollectionListener();
            }
            counter++;
        });
    }

    private void generateItem() {
        subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null) {
                    assert subResultsModels.data != null;
                    if (subResultsModels.data.size() != 0) {
                        sortNat(subResultsModels.data);
                        sortInt(subResultsModels.data);
                        sortDec(subResultsModels.data);
                        makeAdapter();
                        if (resultsDialog.isShowing()){
                            loadOrUpdateDialog(pos,number);
                        }
                    }
                }
        });
    }

    private void makeAdapter() {
        ResultsRecyclerViewAdapter adapter = new ResultsRecyclerViewAdapter(getContext(), this);
        if (!binding.natButton.isEnabled()){
            adapter.setSubItemList(subResultsNaturalsModels,1,3);
        }else if (!binding.intButton.isEnabled()){
            adapter.setSubItemList(subResultsIntegersModels,2,3);
        }else if (!binding.decButton.isEnabled()){
            adapter.setSubItemList(subResultsDecimalsModels,3,3);
        }
        binding.resultRecycler.setAdapter(adapter);
        binding.recyclerProgressBar.setVisibility(View.GONE);
    }

    private void sortNat(ArrayList<SubResultsModel> subResultsModels) {
        mainListsSorter.setSubList(subResultsModels);
        subResultsNaturalsModels = mainListsSorter.sortSubNaturalsModels();
    }

    private void sortInt(ArrayList<SubResultsModel> subResultsModels) {
        mainListsSorter.setSubList(subResultsModels);
        subResultsIntegersModels = mainListsSorter.sortSubIntegersModels();
    }

    private void sortDec(ArrayList<SubResultsModel> subResultsModels) {
        mainListsSorter.setSubList(subResultsModels);
        subResultsDecimalsModels = mainListsSorter.sortSubDecimalsModels();
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

    private void decDialog(int position) {
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
    }

    private void intDialog(int position) {
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
    }

    private void natDialog(int position) {
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
        ListenerRegistration listenerRegistration = firebaseFirestore.collection(USERS).document(id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null && value.exists()) {
                        UserRegisterProfileModel userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                        assert userRegisterProfileModel != null;
                        name = userRegisterProfileModel.getName();
                        country = userRegisterProfileModel.getCountry();
                        if (resultsDialog.isShowing() && this.isVisible()) {
                            requireActivity().runOnUiThread(() -> {
                                resultsDialog.loadData(name, nickname, imageUrl, country, score, tasks);
                                resultsDialog.setDataInViews();
                            });
                        } else if (this.isVisible() && !resultsDialog.isShowing()) {
                            requireActivity().runOnUiThread(() -> {
                                dialogBinding = DialogBottomSheetResultsBinding.inflate(getLayoutInflater());
                                resultsDialog = new ResultsDialog(requireContext(), dialogBinding, name, nickname, imageUrl, country, score, tasks);
                                resultsDialog.show();
                            });
                        }
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_results_recycler;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
