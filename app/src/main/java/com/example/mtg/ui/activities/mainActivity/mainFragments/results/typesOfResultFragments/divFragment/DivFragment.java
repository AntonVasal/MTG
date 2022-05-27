package com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.divFragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.DialogBottomSheetResultsBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.models.countModels.DivResultsModel;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.resultsViewModel.ResultsViewModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.divFragment.divViewModel.DivViewModel;
import com.example.mtg.ui.dialogs.resultsDialog.ResultsDialog;
import com.example.mtg.utility.listsSorters.MainListsSorter;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

public class DivFragment extends BaseBindingFragment<FragmentResultsRecyclerBinding> implements OnItemResultsRecyclerClickInterface {

    private static final String USERS = "users";
    private ResultsDialog resultsDialog;
    private MainListsSorter mainListsSorter;
    private ArrayList<DivResultsModel> divResultsNaturalsModels = new ArrayList<>();
    private ArrayList<DivResultsModel> divResultsIntegersModels = new ArrayList<>();
    private ArrayList<DivResultsModel> divResultsDecimalsModels = new ArrayList<>();
    private ListenerRegistration listenerRegistration;
    private DivViewModel divViewModel;
    private ResultsViewModel resultsViewModel;
    private static int counter = 0;
    private NetworkStateManager networkStateManager;
    private FirebaseFirestore firebaseFirestore;
    private DialogBottomSheetResultsBinding dialogBinding;
    private int pos;
    private int number;
    private String name;
    private String country;
    private String nickname;
    private String imageUrl;
    private String id;
    private int score;
    private int tasks;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkStateManager = NetworkStateManager.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        divViewModel = new ViewModelProvider(requireActivity()).get(DivViewModel.class);
        resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);
        mainListsSorter = new MainListsSorter();

        binding.recyclerProgressBar.setVisibility(View.VISIBLE);
        binding.natButton.setEnabled(false);
        ResultsRecyclerViewAdapter adapter = new ResultsRecyclerViewAdapter(getContext(), this,4);
        adapter.setDivItemList(new ArrayList<>(),1);
        binding.resultRecycler.setAdapter(adapter);
        observeStatus();
        generateItem();
        initListeners();
    }

    private void observeStatus() {
        resultsViewModel.getIsOnPause().observe(getViewLifecycleOwner(), isOnPause -> {
            Boolean isConnect = networkStateManager.getNetworkConnectivityStatus().getValue();
            if (counter != 0 && !isOnPause && isConnect != null && isConnect) {
                divViewModel.loadData();
            } else if (isOnPause) {
                divViewModel.removeCollectionListener();
            }
            counter++;
        });
    }

    private void generateItem() {
        divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
            switch (divResultsModels.status){
                case SUCCESS:
                    assert divResultsModels.data != null;
                    if (divResultsModels.data.size() != 0) {
                        sortNat(divResultsModels.data);
                        sortInt(divResultsModels.data);
                        sortDec(divResultsModels.data);
                        binding.resultsError.setVisibility(View.GONE);
                        makeAdapter();
                        if (resultsDialog!= null && resultsDialog.isShowing()) {
                            loadOrUpdateDialog(pos, number);
                        }
                        binding.recyclerProgressBar.setVisibility(View.GONE);
                    }
                    break;
                case ERROR:
                    binding.resultsError.setVisibility(View.VISIBLE);
                    binding.recyclerProgressBar.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void makeAdapter() {
        ResultsRecyclerViewAdapter adapter = new ResultsRecyclerViewAdapter(getContext(), this,4);
        if (!binding.natButton.isEnabled()) {
            adapter.setDivItemList(divResultsNaturalsModels, 1);
        } else if (!binding.intButton.isEnabled()) {
            adapter.setDivItemList(divResultsIntegersModels, 2);
        } else if (!binding.decButton.isEnabled()) {
            adapter.setDivItemList(divResultsDecimalsModels, 3);
        }
        binding.resultRecycler.setAdapter(adapter);
    }

    private void sortNat(ArrayList<DivResultsModel> divResultsModels) {
        mainListsSorter.setDivList(divResultsModels);
        divResultsNaturalsModels = mainListsSorter.sortDivNaturalsModels();
    }

    private void sortInt(ArrayList<DivResultsModel> divResultsModels) {
        mainListsSorter.setDivList(divResultsModels);
        divResultsIntegersModels = mainListsSorter.sortDivIntegersModels();
    }

    private void sortDec(ArrayList<DivResultsModel> divResultsModels) {
        mainListsSorter.setDivList(divResultsModels);
        divResultsDecimalsModels = mainListsSorter.sortDivDecimalsModels();
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
        Boolean isConnect =  networkStateManager.getNetworkConnectivityStatus().getValue();
        if (isConnect!=null && isConnect){
            loadDataFromFirestoreAndMakeDialogMethod();
        }
    }

    private void intDialog(int position) {
        if (resultsDialog != null && resultsDialog.isShowing()) {
            for (int i = 0; i < divResultsIntegersModels.size(); i++) {
                if (divResultsIntegersModels.get(i).getId().equals(id)) {
                    loadDataIntegerMethod(i);
                }
            }
        } else {
            loadDataIntegerMethod(position);
            id = divResultsIntegersModels.get(position).getId();
        }
    }

    private void decDialog(int position) {
        if (resultsDialog != null && resultsDialog.isShowing()) {
            for (int i = 0; i < divResultsDecimalsModels.size(); i++) {
                if (divResultsDecimalsModels.get(i).getId().equals(id)) {
                    loadDataDecimalMethod(i);
                }
            }
        } else {
            loadDataDecimalMethod(position);
            id = divResultsDecimalsModels.get(position).getId();
        }
    }

    private void natDialog(int position) {
        if (resultsDialog != null && resultsDialog.isShowing()) {
            for (int i = 0; i < divResultsNaturalsModels.size(); i++) {
                if (divResultsNaturalsModels.get(i).getId().equals(id)) {
                    loadDataNaturalMethod(i);
                }
            }
        } else {
            loadDataNaturalMethod(position);
            id = divResultsNaturalsModels.get(position).getId();
        }
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
        resultsDialog = new ResultsDialog(requireContext(),null,"","","","",0,0);
        listenerRegistration = firebaseFirestore.collection(USERS).document(id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null && value.exists()) {
                        UserRegisterProfileModel userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                        assert userRegisterProfileModel != null;
                        name = userRegisterProfileModel.getName();
                        country = userRegisterProfileModel.getCountry();
                        if (resultsDialog!=null && resultsDialog.isShowing() && this.isVisible()) {
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
                        resultsDialog.setOnDismissListener(dialogInterface -> listenerRegistration.remove());
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
