package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.databinding.BottomSheetResultsDialogBinding;
import com.example.mtg.databinding.FragmentResultsRecyclerBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.example.mtg.mainActivity.count.countModels.AddResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.AddViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddFragment extends Fragment implements OnItemResultsRecyclerClickInterface {

    private AddViewModel addViewModel;


    private ResultsRecyclerViewAdapter adapter;
    private BottomSheetDialog bottomSheetDialog;

    private ArrayList<AddResultsModel> addResultsNaturalsModels;
    private ArrayList<AddResultsModel> addResultsIntegersModels;
    private ArrayList<AddResultsModel> addResultsDecimalsModels;
    private static final String USERS = "users";

    private String name;
    private String country;

    private FragmentResultsRecyclerBinding binding;
    private BottomSheetResultsDialogBinding dialogBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);
        dialogBinding = BottomSheetResultsDialogBinding.inflate(inflater,container,false);
        bottomSheetDialog = new BottomSheetDialog(requireActivity());
        bottomSheetDialog.setContentView(dialogBinding.getRoot());
        View view = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.resultRecycler.setLayoutManager(layoutManager);

        addViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateItem();
        initListeners();
        binding.natButton.setEnabled(false);
//        binding.recyclerProgressBar.setVisibility(View.VISIBLE);
    }

    private void generateItem() {
        addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
            if (userResultsModels != null && userResultsModels.size() != 0) {
                userResultsModels.sort((addResultsModel, t1) -> t1.getAddNaturalScore() - addResultsModel.getAddNaturalScore());
                addResultsNaturalsModels = new ArrayList<>(userResultsModels);
                addResultsNaturalsModels.removeIf(addResultsModel -> addResultsModel.getAddNaturalScore() == 0);
                adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 1, this);
                adapter.setAddItemList(addResultsNaturalsModels);
                binding.resultRecycler.setAdapter(adapter);
//                binding.recyclerProgressBar.setVisibility(View.GONE);
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
                    userResultsModels.sort((addResultsModel, t1) -> t1.getAddIntegerScore() - addResultsModel.getAddIntegerScore());
                    addResultsIntegersModels = new ArrayList<>(userResultsModels);
                    addResultsIntegersModels.removeIf(addResultsModel -> addResultsModel.getAddIntegerScore() == 0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 2, this);
                    adapter.setAddItemList(addResultsIntegersModels);
                    binding.resultRecycler.setAdapter(adapter);
//                    binding.recyclerProgressBar.setVisibility(View.GONE);
                }
            });
        });

        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.intButton.setEnabled(true);
            binding.decButton.setEnabled(false);
            addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null && userResultsModels.size() != 0) {
                    userResultsModels.sort((addResultsModel, t1) -> t1.getAddDecimalScore() - addResultsModel.getAddDecimalScore());
                    addResultsDecimalsModels = new ArrayList<>(userResultsModels);
                    addResultsDecimalsModels.removeIf(addResultsModel -> addResultsModel.getAddDecimalScore() == 0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(), 1, 3, this);
                    adapter.setAddItemList(addResultsDecimalsModels);
                    binding.resultRecycler.setAdapter(adapter);
//                    binding.recyclerProgressBar.setVisibility(View.GONE);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        dialogBinding.exitButtonBottomDialog.setOnClickListener(view -> bottomSheetDialog.cancel());

        switch (typeNumber) {
            case 1:
                addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), addResultsModels -> {
                    addResultsModels.sort((addResultsModel, t1) -> t1.getAddNaturalScore() - addResultsModel.getAddNaturalScore());
                    addResultsNaturalsModels = new ArrayList<>(addResultsModels);
                    addResultsNaturalsModels.removeIf(addResultsModel -> addResultsModel.getAddNaturalScore() == 0);
                    dialogBinding.infoScoreDialog.setText(String.valueOf(addResultsNaturalsModels.get(position).getAddNaturalScore()));
                    dialogBinding.infoTasksDialog.setText(String.valueOf(addResultsNaturalsModels.get(position).getAddNaturalTasksAmount()));
                    Glide.with(requireActivity()).load(addResultsNaturalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(dialogBinding.dialogImage);

                    dialogBinding.nicknameTextDialog.setText(addResultsNaturalsModels.get(position).getNickname());

                    dialogBinding.infoNicknameDialog.setText(addResultsNaturalsModels.get(position).getNickname());

                    firebaseFirestore.collection(USERS).document(addResultsNaturalsModels.get(position).getId())
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    return;
                                }
                                if (value != null && value.exists()) {
                                    UserRegisterProfileModel userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                                    assert userRegisterProfileModel != null;
                                    name = userRegisterProfileModel.getName();
                                    country = userRegisterProfileModel.getCountry();

                                    dialogBinding.infoNameDialog.setText(name);

                                    dialogBinding.infoCountryDialog.setText(country);
                                }
                            });
                });
                bottomSheetDialog.show();
                break;
            case 2:
                addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), addResultsModels -> {
                    addResultsModels.sort((addResultsModel, t1) -> t1.getAddIntegerScore() - addResultsModel.getAddIntegerScore());
                    addResultsIntegersModels = new ArrayList<>(addResultsModels);
                    addResultsIntegersModels.removeIf(addResultsModel -> addResultsModel.getAddIntegerScore() == 0);
                    dialogBinding.infoScoreDialog.setText(String.valueOf(addResultsIntegersModels.get(position).getAddIntegerScore()));
                    dialogBinding.infoTasksDialog.setText(String.valueOf(addResultsIntegersModels.get(position).getAddIntegerTasksAmount()));
                    Glide.with(requireActivity()).load(addResultsIntegersModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(dialogBinding.dialogImage);
                    dialogBinding.nicknameTextDialog.setText(addResultsIntegersModels.get(position).getNickname());

                    dialogBinding.infoNicknameDialog.setText(addResultsIntegersModels.get(position).getNickname());

                    firebaseFirestore.collection(USERS).document(addResultsIntegersModels.get(position).getId())
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    return;
                                }
                                if (value != null && value.exists()) {
                                    UserRegisterProfileModel userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                                    assert userRegisterProfileModel != null;
                                    name = userRegisterProfileModel.getName();
                                    country = userRegisterProfileModel.getCountry();

                                    dialogBinding.infoNameDialog.setText(name);

                                    dialogBinding.infoCountryDialog.setText(country);
                                }
                            });
                });
                bottomSheetDialog.show();
                break;
            case 3:
                addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), addResultsModels -> {
                    addResultsModels.sort((addResultsModel, t1) -> t1.getAddDecimalScore() - addResultsModel.getAddDecimalScore());
                    addResultsDecimalsModels = new ArrayList<>(addResultsModels);
                    addResultsDecimalsModels.removeIf(addResultsModel -> addResultsModel.getAddDecimalScore() == 0);
                    dialogBinding.infoScoreDialog.setText(String.valueOf(addResultsDecimalsModels.get(position).getAddDecimalScore()));
                    dialogBinding.infoTasksDialog.setText(String.valueOf(addResultsDecimalsModels.get(position).getAddDecimalTasksAmount()));
                    Glide.with(requireActivity()).load(addResultsDecimalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(dialogBinding.dialogImage);

                    dialogBinding.nicknameTextDialog.setText(addResultsDecimalsModels.get(position).getNickname());

                    dialogBinding.infoNicknameDialog.setText(addResultsDecimalsModels.get(position).getNickname());

                    firebaseFirestore.collection(USERS).document(addResultsDecimalsModels.get(position).getId())
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    return;
                                }
                                if (value != null && value.exists()) {
                                    UserRegisterProfileModel userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                                    assert userRegisterProfileModel != null;
                                    name = userRegisterProfileModel.getName();
                                    country = userRegisterProfileModel.getCountry();
                                    dialogBinding.infoNameDialog.setText(name);
                                    dialogBinding.infoCountryDialog.setText(country);
                                }
                            });
                });
                bottomSheetDialog.show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}