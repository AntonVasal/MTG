package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.R;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.example.mtg.mainActivity.count.countModels.SubResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.SubViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SubFragment extends Fragment implements OnItemResultsRecyclerClickInterface {
    private Button natButton;
    private Button intButton;
    private Button decButton;

    private RecyclerView recyclerView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results_recycler, container, false);
        natButton = view.findViewById(R.id.nat_button);
        intButton = view.findViewById(R.id.int_button);
        decButton = view.findViewById(R.id.dec_button);
        natButton.setEnabled(false);
        initListeners();

        subViewModel = new ViewModelProvider(requireActivity()).get(SubViewModel.class);


        recyclerView = view.findViewById(R.id.result_recycler);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateItem();
    }

    private void generateItem() {
        subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
            if (subResultsModels != null && subResultsModels.size()!=0){
                subResultsModels.sort((subResultsModel, t1) -> t1.getSubNaturalScore() - subResultsModel.getSubNaturalScore());
                subResultsNaturalsModels = new ArrayList<>(subResultsModels);
                subResultsNaturalsModels.removeIf(subResultsModel -> subResultsModel.getSubNaturalScore()==0);
                adapter = new ResultsRecyclerViewAdapter(getContext(),3,1,this);
                adapter.setSubItemList(subResultsNaturalsModels);
                recyclerView.setAdapter(adapter);
            }
        });
    }
    private void initListeners() {
        natButton.setOnClickListener(view -> {
            natButton.setEnabled(false);
            natButton.setTextColor(getResources().getColor(R.color.blue,null));
            intButton.setEnabled(true);
            intButton.setTextColor(getResources().getColor(R.color.white,null));
            decButton.setEnabled(true);
            decButton.setTextColor(getResources().getColor(R.color.white,null));

            generateItem();
        });
        intButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            natButton.setTextColor(getResources().getColor(R.color.white,null));
            intButton.setEnabled(false);
            intButton.setTextColor(getResources().getColor(R.color.blue,null));
            decButton.setEnabled(true);
            decButton.setTextColor(getResources().getColor(R.color.white,null));

            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null && subResultsModels.size()!=0){
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubIntegerScore() - subResultsModel.getSubIntegerScore());
                    subResultsIntegersModels = new ArrayList<>(subResultsModels);
                    subResultsIntegersModels.removeIf(subResultsModel -> subResultsModel.getSubIntegerScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),3,2,this);
                    adapter.setSubItemList(subResultsIntegersModels);
                    recyclerView.setAdapter(adapter);
                }
            });
        });
        decButton.setOnClickListener(view -> {
            natButton.setEnabled(true);
            natButton.setTextColor(getResources().getColor(R.color.white,null));
            intButton.setEnabled(true);
            intButton.setTextColor(getResources().getColor(R.color.white,null));
            decButton.setEnabled(false);
            decButton.setTextColor(getResources().getColor(R.color.blue,null));

            subViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), subResultsModels -> {
                if (subResultsModels != null && subResultsModels.size()!=0){
                    subResultsModels.sort((subResultsModel, t1) -> t1.getSubDecimalScore() - subResultsModel.getSubDecimalScore());
                    subResultsDecimalsModels = new ArrayList<>(subResultsModels);
                    subResultsDecimalsModels.removeIf(subResultsModel -> subResultsModel.getSubDecimalScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),3,3,this);
                    adapter.setSubItemList(subResultsDecimalsModels);
                    recyclerView.setAdapter(adapter);
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
