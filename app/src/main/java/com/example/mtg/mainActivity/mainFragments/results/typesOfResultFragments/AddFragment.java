package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    LinearLayoutManager layoutManager;

    ArrayList<AddResultsModel> addResultsNaturalsModels;
    ArrayList<AddResultsModel> addResultsIntegersModels;
    ArrayList<AddResultsModel> addResultsDecimalsModels;

    String name;
    String country;

    private FragmentResultsRecyclerBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        layoutManager = new LinearLayoutManager(getContext());
        binding.resultRecycler.setLayoutManager(layoutManager);

        addViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);


        binding.natButton.setEnabled(false);
        initListeners();

        binding.recyclerProgressBar.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateItem();

    }

    private void generateItem() {
        addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
            if (userResultsModels != null && userResultsModels.size() != 0) {
                userResultsModels.sort((addResultsModel, t1) -> t1.getAddNaturalScore() - addResultsModel.getAddNaturalScore());
                addResultsNaturalsModels = new ArrayList<>(userResultsModels);
                addResultsNaturalsModels.removeIf(addResultsModel -> addResultsModel.getAddNaturalScore()==0);
                adapter = new ResultsRecyclerViewAdapter(getContext(),1,1,this);
                adapter.setAddItemList(addResultsNaturalsModels);
                binding.resultRecycler.setAdapter(adapter);
                binding.recyclerProgressBar.setVisibility(View.GONE);
            }
        });
    }


    private void initListeners() {
        binding.natButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(false);
            binding.natButton.setTextColor(getResources().getColor(R.color.blue, null));
            binding.intButton.setEnabled(true);
            binding.intButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.decButton.setEnabled(true);
            binding.decButton.setTextColor(getResources().getColor(R.color.white, null));

            generateItem();
        });

        binding.intButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.natButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.intButton.setEnabled(false);
            binding.intButton.setTextColor(getResources().getColor(R.color.blue, null));
            binding.decButton.setEnabled(true);
            binding.decButton.setTextColor(getResources().getColor(R.color.white, null));

            addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null && userResultsModels.size() != 0) {
                    userResultsModels.sort((addResultsModel, t1) -> t1.getAddIntegerScore() - addResultsModel.getAddIntegerScore());
                    addResultsIntegersModels = new ArrayList<>(userResultsModels);
                    addResultsIntegersModels.removeIf(addResultsModel -> addResultsModel.getAddIntegerScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),1,2,this);
                    adapter.setAddItemList(addResultsIntegersModels);
                    binding.resultRecycler.setAdapter(adapter);
                    binding.recyclerProgressBar.setVisibility(View.GONE);
                }
            });
        });

        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.natButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.intButton.setEnabled(true);
            binding.intButton.setTextColor(getResources().getColor(R.color.white, null));
            binding.decButton.setEnabled(false);
            binding.decButton.setTextColor(getResources().getColor(R.color.blue, null));

            addViewModel.getUserResultsModel().observe(getViewLifecycleOwner(), userResultsModels -> {
                if (userResultsModels != null && userResultsModels.size() != 0) {
                    userResultsModels.sort((addResultsModel, t1) -> t1.getAddDecimalScore() - addResultsModel.getAddDecimalScore());
                    addResultsDecimalsModels = new ArrayList<>(userResultsModels);
                    addResultsDecimalsModels.removeIf(addResultsModel -> addResultsModel.getAddDecimalScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),1,3,this);
                    adapter.setAddItemList(addResultsDecimalsModels);
                    binding.resultRecycler.setAdapter(adapter);
                    binding.recyclerProgressBar.setVisibility(View.GONE);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_results_dialog);

        ImageView userImgView = bottomSheetDialog.findViewById(R.id.dialog_image);
        TextView nicknameTextView = bottomSheetDialog.findViewById(R.id.nickname_text_dialog);
        TextView nicknameInfo = bottomSheetDialog.findViewById(R.id.info_nickname_dialog);
        TextView nameInfo = bottomSheetDialog.findViewById(R.id.info_name_dialog);
        TextView countryInfo = bottomSheetDialog.findViewById(R.id.info_country_dialog);
        TextView scoreInfo = bottomSheetDialog.findViewById(R.id.info_score_dialog);
        TextView tasksInfo = bottomSheetDialog.findViewById(R.id.info_tasks_dialog);

        assert userImgView != null;
        Glide.with(requireActivity()).load(addResultsNaturalsModels.get(position).getImageUrl())
                .apply(new RequestOptions().centerCrop()).into(userImgView);

        assert nicknameTextView != null;
        nicknameTextView.setText(addResultsNaturalsModels.get(position).getNickname());

        assert nicknameInfo != null;
        nicknameInfo.setText(addResultsNaturalsModels.get(position).getNickname());

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").document(addResultsNaturalsModels.get(position).getId())
                .addSnapshotListener((value, error) -> {
                    if (error != null){
                        return;
                    }
                    if (value != null && value.exists()){
                        UserRegisterProfileModel userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                        assert userRegisterProfileModel != null;
                        name = userRegisterProfileModel.getName();
                        country = userRegisterProfileModel.getCountry();

                        assert nameInfo != null;
                        nameInfo.setText(name);

                        assert countryInfo != null;
                        countryInfo.setText(country);
                    }
                });

        switch (typeNumber){
            case 1:
                assert scoreInfo != null;
                scoreInfo.setText(String.valueOf(addResultsNaturalsModels.get(position).getAddNaturalScore()));
                assert tasksInfo != null;
                tasksInfo.setText(String.valueOf(addResultsNaturalsModels.get(position).getAddNaturalTasksAmount()));
                bottomSheetDialog.show();
                break;
            case 2:
                assert scoreInfo != null;
                scoreInfo.setText(String.valueOf(addResultsIntegersModels.get(position).getAddIntegerScore()));
                assert tasksInfo != null;
                tasksInfo.setText(String.valueOf(addResultsIntegersModels.get(position).getAddIntegerTasksAmount()));
                bottomSheetDialog.show();
                break;
            case 3:
                assert scoreInfo != null;
                scoreInfo.setText(String.valueOf(addResultsDecimalsModels.get(position).getAddDecimalScore()));
                assert tasksInfo != null;
                tasksInfo.setText(String.valueOf(addResultsDecimalsModels.get(position).getAddDecimalTasksAmount()));
                bottomSheetDialog.show();
                break;
        }
    }
}