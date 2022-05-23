package com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.multiFragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.DialogBottomSheetResultsBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.models.countModels.MultiResultsModel;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.resultsViewModel.ResultsViewModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.multiFragment.multiViewModel.MultiViewModel;
import com.example.mtg.ui.dialogs.resultsDialog.ResultsDialog;
import com.example.mtg.utility.listsSorters.MainListsSorter;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MultiFragment extends BaseBindingFragment<FragmentResultsRecyclerBinding> implements OnItemResultsRecyclerClickInterface {

    private static final String USERS = "users";

    private ResultsRecyclerViewAdapter adapter;
    private ResultsDialog resultsDialog;
    private MainListsSorter mainListsSorter;
    private String name;
    private String country;
    private String nickname;
    private String imageUrl;
    private String id;
    private int score;
    private int tasks;
    private static int counter = 0;
    private NetworkStateManager networkStateManager;
    private ArrayList<MultiResultsModel> multiResultsNaturalsModels;
    private ArrayList<MultiResultsModel> multiResultsIntegersModels;
    private ArrayList<MultiResultsModel> multiResultsDecimalsModels;

    private ResultsViewModel resultsViewModel;
    private MultiViewModel multiViewModel;

    private FirebaseFirestore firebaseFirestore;
    private DialogBottomSheetResultsBinding dialogBinding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkStateManager = NetworkStateManager.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        multiViewModel = new ViewModelProvider(requireActivity()).get(MultiViewModel.class);
        resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);

        mainListsSorter = new MainListsSorter();

        binding.recyclerProgressBar.setVisibility(View.VISIBLE);
        binding.natButton.setEnabled(false);

//        observeStatus();
        generateItem();
        initListeners();
    }

//    private void observeStatus() {
//        resultsViewModel.getIsOnPause().observe(getViewLifecycleOwner(), isOnPause -> {
//            Boolean isConnect = networkStateManager.getNetworkConnectivityStatus().getValue();
//            if (counter != 0 && !isOnPause && isConnect != null && isConnect) {
//                multiViewModel.loadData();
//            }else if (isOnPause){
//                multiViewModel.removeCollectionListener();
//            }
//            counter++;
//        });
//    }

    private void generateItem() {
        multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
            if (multiResultsModels != null) {
                assert multiResultsModels.data != null;
                if (multiResultsModels.data.size() != 0) {
                    mainListsSorter.setMultiList(multiResultsModels.data);
                    multiResultsNaturalsModels = mainListsSorter.sortMultiNaturalsModels();
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 2, 1, this);
                    adapter.setMultiItemList(multiResultsNaturalsModels);
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
            multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                if (multiResultsModels != null) {
                    assert multiResultsModels.data != null;
                    if (multiResultsModels.data.size() != 0) {
                        mainListsSorter.setMultiList(multiResultsModels.data);
                        multiResultsIntegersModels = mainListsSorter.sortMultiIntegersModels();
                        adapter = new ResultsRecyclerViewAdapter(getContext(), 2, 2, this);
                        adapter.setMultiItemList(multiResultsIntegersModels);
                        binding.resultRecycler.setAdapter(adapter);
                    }
                }
            });

        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                if (multiResultsModels != null) {
                    assert multiResultsModels.data != null;
                    if (multiResultsModels.data.size() != 0) {
                        mainListsSorter.setMultiList(multiResultsModels.data);
                        multiResultsDecimalsModels = mainListsSorter.sortMultiDecimalsModels();
                        adapter = new ResultsRecyclerViewAdapter(getContext(), 2, 3, this);
                        adapter.setMultiItemList(multiResultsDecimalsModels);
                        binding.resultRecycler.setAdapter(adapter);
                    }
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
            switch (typeNumber) {
                case 1:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < multiResultsNaturalsModels.size(); i++) {
                            if (multiResultsNaturalsModels.get(i).getId().equals(id)) {
                                loadDataNaturalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataNaturalMethod(position);
                        id = multiResultsNaturalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 2:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < multiResultsIntegersModels.size(); i++) {
                            if (multiResultsIntegersModels.get(i).getId().equals(id)) {
                                loadDataIntegerMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataIntegerMethod(position);
                        id = multiResultsIntegersModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
                case 3:
                    if (resultsDialog != null && resultsDialog.isShowing()) {
                        for (int i = 0; i < multiResultsDecimalsModels.size(); i++) {
                            if (multiResultsDecimalsModels.get(i).getId().equals(id)) {
                                loadDataDecimalMethod(i);
                                loadDataFromFirestoreAndMakeDialogMethod();
                            }
                        }
                    } else {
                        loadDataDecimalMethod(position);
                        id = multiResultsDecimalsModels.get(position).getId();
                        loadDataFromFirestoreAndMakeDialogMethod();
                    }
                    break;
            }
        });
    }

    private void loadDataNaturalMethod(int position) {
        score = multiResultsNaturalsModels.get(position).getMultiNaturalScore();
        tasks = multiResultsNaturalsModels.get(position).getMultiNaturalTasksAmount();
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_results_recycler;
    }
}
