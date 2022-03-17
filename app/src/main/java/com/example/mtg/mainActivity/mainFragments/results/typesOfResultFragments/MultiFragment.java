package com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments;

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
import com.example.mtg.mainActivity.count.countModels.MultiResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.MultiViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MultiFragment extends Fragment implements OnItemResultsRecyclerClickInterface {
    private Button natButton;
    private Button intButton;
    private Button decButton;

    private RecyclerView recyclerView;
    private ResultsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    String name;
    String country;

    ArrayList<MultiResultsModel> multiResultsNaturalsModels;
    ArrayList<MultiResultsModel> multiResultsIntegersModels;
    ArrayList<MultiResultsModel> multiResultsDecimalsModels;

    private MultiViewModel multiViewModel;

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

        multiViewModel = new ViewModelProvider(requireActivity()).get(MultiViewModel.class);

        recyclerView = view.findViewById(R.id.result_recycler);
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
        multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
            if (multiResultsModels != null && multiResultsModels.size() != 0){
                multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiNaturalScore() - multiResultsModel.getMultiNaturalScore());
                multiResultsNaturalsModels = new ArrayList<>(multiResultsModels);
                multiResultsNaturalsModels.removeIf(multiResultsModel -> multiResultsModel.getMultiNaturalScore()==0);
                adapter = new ResultsRecyclerViewAdapter(getContext(),2,1,this);
                adapter.setMultiItemList(multiResultsNaturalsModels);
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

            multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                if (multiResultsModels != null && multiResultsModels.size() != 0){
                    multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiIntegerScore() - multiResultsModel.getMultiIntegerScore());
                    multiResultsIntegersModels = new ArrayList<>(multiResultsModels);
                    multiResultsIntegersModels.removeIf(multiResultsModel -> multiResultsModel.getMultiIntegerScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),2,2,this);
                    adapter.setMultiItemList(multiResultsIntegersModels);
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

            multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                if (multiResultsModels != null && multiResultsModels.size() != 0){
                    multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiDecimalScore() - multiResultsModel.getMultiDecimalScore());
                    multiResultsDecimalsModels = new ArrayList<>(multiResultsModels);
                    multiResultsDecimalsModels.removeIf(multiResultsModel -> multiResultsModel.getMultiDecimalScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),2,3,this);
                    adapter.setMultiItemList(multiResultsDecimalsModels);
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
                multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                    multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiNaturalScore() - multiResultsModel.getMultiNaturalScore());
                    multiResultsNaturalsModels = new ArrayList<>(multiResultsModels);
                    multiResultsNaturalsModels.removeIf(multiResultsModel -> multiResultsModel.getMultiNaturalScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(multiResultsNaturalsModels.get(position).getMultiNaturalScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(multiResultsNaturalsModels.get(position).getMultiNaturalTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(multiResultsNaturalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(multiResultsNaturalsModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(multiResultsNaturalsModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(multiResultsNaturalsModels.get(position).getId())
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
                multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                    multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiIntegerScore() - multiResultsModel.getMultiIntegerScore());
                    multiResultsIntegersModels = new ArrayList<>(multiResultsModels);
                    multiResultsIntegersModels.removeIf(multiResultsModel -> multiResultsModel.getMultiIntegerScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(multiResultsIntegersModels.get(position).getMultiIntegerScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(multiResultsIntegersModels.get(position).getMultiIntegerTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(multiResultsIntegersModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(multiResultsIntegersModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(multiResultsIntegersModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(multiResultsIntegersModels.get(position).getId())
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
                multiViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), multiResultsModels -> {
                    multiResultsModels.sort((multiResultsModel, t1) -> t1.getMultiDecimalScore() - multiResultsModel.getMultiDecimalScore());
                    multiResultsDecimalsModels = new ArrayList<>(multiResultsModels);
                    multiResultsDecimalsModels.removeIf(multiResultsModel -> multiResultsModel.getMultiDecimalScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(multiResultsDecimalsModels.get(position).getMultiDecimalScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(multiResultsDecimalsModels.get(position).getMultiDecimalTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(multiResultsDecimalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(multiResultsDecimalsModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(multiResultsDecimalsModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(multiResultsDecimalsModels.get(position).getId())
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
