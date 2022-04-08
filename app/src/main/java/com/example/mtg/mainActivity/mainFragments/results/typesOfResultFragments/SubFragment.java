package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.example.mtg.mainActivity.count.countModels.SubResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.SubViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SubFragment extends Fragment implements OnItemResultsRecyclerClickInterface {

    private FragmentResultsRecyclerBinding binding;

    private ResultsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    String name;
    String country;

    ArrayList<SubResultsModel> subResultsNaturalsModels;
    ArrayList<SubResultsModel> subResultsIntegersModels;
    ArrayList<SubResultsModel> subResultsDecimalsModels;

    private SubViewModel subViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResultsRecyclerBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        subViewModel = new ViewModelProvider(requireActivity()).get(SubViewModel.class);


        binding.resultRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        binding.resultRecycler.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateItem();
        binding.natButton.setEnabled(false);
        initListeners();
    }

    private void generateItem() {
        subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
            if (subResultsModels != null && subResultsModels.size()!=0){
                subResultsModels.sort((subResultsModel, t1) -> t1.getSubNaturalScore() - subResultsModel.getSubNaturalScore());
                subResultsNaturalsModels = new ArrayList<>(subResultsModels);
                subResultsNaturalsModels.removeIf(subResultsModel -> subResultsModel.getSubNaturalScore()==0);
                adapter = new ResultsRecyclerViewAdapter(getContext(),3,1,this);
                adapter.setSubItemList(subResultsNaturalsModels);
                binding.resultRecycler.setAdapter(adapter);
            }
        });
    }
    private void initListeners() {
        binding.natButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(false);
            binding.natButton.setTextColor(getResources().getColor(R.color.blue,null));
            binding.intButton.setEnabled(true);
            binding.intButton.setTextColor(getResources().getColor(R.color.white,null));
            binding.decButton.setEnabled(true);
            binding.decButton.setTextColor(getResources().getColor(R.color.white,null));

            generateItem();
        });
        binding.intButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.natButton.setTextColor(getResources().getColor(R.color.white,null));
            binding.intButton.setEnabled(false);
            binding.intButton.setTextColor(getResources().getColor(R.color.blue,null));
            binding.decButton.setEnabled(true);
            binding.decButton.setTextColor(getResources().getColor(R.color.white,null));

            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null && subResultsModels.size()!=0){
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubIntegerScore() - subResultsModel.getSubIntegerScore());
                    subResultsIntegersModels = new ArrayList<>(subResultsModels);
                    subResultsIntegersModels.removeIf(subResultsModel -> subResultsModel.getSubIntegerScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),3,2,this);
                    adapter.setSubItemList(subResultsIntegersModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });
        });
        binding.decButton.setOnClickListener(view -> {
            binding.natButton.setEnabled(true);
            binding.natButton.setTextColor(getResources().getColor(R.color.white,null));
            binding.intButton.setEnabled(true);
            binding.intButton.setTextColor(getResources().getColor(R.color.white,null));
            binding.decButton.setEnabled(false);
            binding.decButton.setTextColor(getResources().getColor(R.color.blue,null));

            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null && subResultsModels.size()!=0){
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubDecimalScore() - subResultsModel.getSubDecimalScore());
                    subResultsDecimalsModels = new ArrayList<>(subResultsModels);
                    subResultsDecimalsModels.removeIf(subResultsModel -> subResultsModel.getSubDecimalScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),3,3,this);
                    adapter.setSubItemList(subResultsDecimalsModels);
                    binding.resultRecycler.setAdapter(adapter);
                }
            });
        });
    }

    @Override
    public void onItemClick(int position, int typeNumber) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_results_dialog);

        ImageView userImgView = bottomSheetDialog.findViewById(R.id.dialog_image);
        ImageButton imageButton = bottomSheetDialog.findViewById(R.id.exit_button_bottom_dialog);
        TextView nicknameTextView = bottomSheetDialog.findViewById(R.id.nickname_text_dialog);
        TextView nicknameInfo = bottomSheetDialog.findViewById(R.id.info_nickname_dialog);
        TextView nameInfo = bottomSheetDialog.findViewById(R.id.info_name_dialog);
        TextView countryInfo = bottomSheetDialog.findViewById(R.id.info_country_dialog);
        TextView scoreInfo = bottomSheetDialog.findViewById(R.id.info_score_dialog);
        TextView tasksInfo = bottomSheetDialog.findViewById(R.id.info_tasks_dialog);


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        assert imageButton != null;
        imageButton.setOnClickListener(view -> bottomSheetDialog.cancel());

        switch (typeNumber){
            case 1:
                subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubNaturalScore() - subResultsModel.getSubNaturalScore());
                    subResultsNaturalsModels = new ArrayList<>(subResultsModels);
                    subResultsNaturalsModels.removeIf(subResultsModel -> subResultsModel.getSubNaturalScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(subResultsNaturalsModels.get(position).getSubNaturalScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(subResultsNaturalsModels.get(position).getSubNaturalTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(subResultsNaturalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(subResultsNaturalsModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(subResultsNaturalsModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(subResultsNaturalsModels.get(position).getId())
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    return;
                                }
                                if (value != null && value.exists()) {
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
                });
                bottomSheetDialog.show();
                break;
            case 2:
                subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubIntegerScore() - subResultsModel.getSubIntegerScore());
                    subResultsIntegersModels = new ArrayList<>(subResultsModels);
                    subResultsIntegersModels.removeIf(subResultsModel -> subResultsModel.getSubIntegerScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(subResultsIntegersModels.get(position).getSubIntegerScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(subResultsIntegersModels.get(position).getSubIntegerTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(subResultsIntegersModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(subResultsIntegersModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(subResultsIntegersModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(subResultsIntegersModels.get(position).getId())
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    return;
                                }
                                if (value != null && value.exists()) {
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
                });
                bottomSheetDialog.show();
                break;
            case 3:
                subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubDecimalScore() - subResultsModel.getSubDecimalScore());
                    subResultsDecimalsModels = new ArrayList<>(subResultsModels);
                    subResultsDecimalsModels.removeIf(subResultsModel -> subResultsModel.getSubDecimalScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(subResultsDecimalsModels.get(position).getSubDecimalScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(subResultsDecimalsModels.get(position).getSubDecimalTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(subResultsDecimalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(subResultsDecimalsModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(subResultsDecimalsModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(subResultsDecimalsModels.get(position).getId())
                            .addSnapshotListener((value, error) -> {
                                if (error != null) {
                                    return;
                                }
                                if (value != null && value.exists()) {
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
                });
                bottomSheetDialog.show();
                break;
        }
    }
}
