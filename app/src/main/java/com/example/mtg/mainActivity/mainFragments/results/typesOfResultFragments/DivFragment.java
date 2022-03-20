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
import com.example.mtg.mainActivity.count.countModels.DivResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.OnItemResultsRecyclerClickInterface;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter.ResultsRecyclerViewAdapter;
import com.example.mtg.mainActivity.mainFragments.results.viewModels.DivViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DivFragment extends Fragment implements OnItemResultsRecyclerClickInterface {
    private Button natButton;
    private Button intButton;
    private Button decButton;

    String name;
    String country;

    private RecyclerView recyclerView;
    private ResultsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    ArrayList<DivResultsModel> divResultsNaturalsModels;
    ArrayList<DivResultsModel> divResultsIntegersModels;
    ArrayList<DivResultsModel> divResultsDecimalsModels;

    private DivViewModel divViewModel;


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

        divViewModel = new ViewModelProvider(requireActivity()).get(DivViewModel.class);



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
        divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
            if (divResultsModels!=null && divResultsModels.size()!=0){
                divResultsModels.sort((divResultsModel, t1) -> t1.getDivNaturalScore() - divResultsModel.getDivNaturalScore());
                divResultsNaturalsModels = new ArrayList<>(divResultsModels);
                divResultsNaturalsModels.removeIf(divResultsModel -> divResultsModel.getDivNaturalScore()==0);
                adapter = new ResultsRecyclerViewAdapter(getContext(),4,1,this);
                adapter.setDivItemList(divResultsNaturalsModels);
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

            divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                if (divResultsModels!=null && divResultsModels.size()!=0){
                    divResultsModels.sort((divResultsModel, t1) -> t1.getDivIntegerScore() - divResultsModel.getDivIntegerScore());
                    divResultsIntegersModels = new ArrayList<>(divResultsModels);
                    divResultsIntegersModels.removeIf(divResultsModel -> divResultsModel.getDivIntegerScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),4,2,this);
                    adapter.setDivItemList(divResultsIntegersModels);
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

            divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                if (divResultsModels!=null && divResultsModels.size()!=0){
                    divResultsModels.sort((divResultsModel, t1) -> t1.getDivDecimalScore() - divResultsModel.getDivDecimalScore());
                    divResultsDecimalsModels = new ArrayList<>(divResultsModels);
                    divResultsDecimalsModels.removeIf(divResultsModel -> divResultsModel.getDivDecimalScore()==0);
                    adapter = new ResultsRecyclerViewAdapter(getContext(),4,3,this);
                    adapter.setDivItemList(divResultsDecimalsModels);
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
                divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                    divResultsModels.sort((divResultsModel, t1) -> t1.getDivNaturalScore() - divResultsModel.getDivNaturalScore());
                    divResultsNaturalsModels = new ArrayList<>(divResultsModels);
                    divResultsNaturalsModels.removeIf(divResultsModel -> divResultsModel.getDivNaturalScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(divResultsNaturalsModels.get(position).getDivNaturalScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(divResultsNaturalsModels.get(position).getDivNaturalTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(divResultsNaturalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(divResultsNaturalsModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(divResultsNaturalsModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(divResultsNaturalsModels.get(position).getId())
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
                divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                    divResultsModels.sort((divResultsModel, t1) -> t1.getDivIntegerScore() - divResultsModel.getDivIntegerScore());
                    divResultsIntegersModels = new ArrayList<>(divResultsModels);
                    divResultsIntegersModels.removeIf(divResultsModel -> divResultsModel.getDivIntegerScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(divResultsIntegersModels.get(position).getDivIntegerScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(divResultsIntegersModels.get(position).getDivIntegerTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(divResultsIntegersModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(divResultsIntegersModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(divResultsIntegersModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(divResultsIntegersModels.get(position).getId())
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
                divViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), divResultsModels -> {
                    divResultsModels.sort((divResultsModel, t1) -> t1.getDivDecimalScore() - divResultsModel.getDivDecimalScore());
                    divResultsDecimalsModels = new ArrayList<>(divResultsModels);
                    divResultsDecimalsModels.removeIf(divResultsModel -> divResultsModel.getDivDecimalScore()==0);
                    assert scoreInfo != null;
                    scoreInfo.setText(String.valueOf(divResultsDecimalsModels.get(position).getDivDecimalScore()));
                    assert tasksInfo != null;
                    tasksInfo.setText(String.valueOf(divResultsDecimalsModels.get(position).getDivDecimalTasksAmount()));
                    assert userImgView != null;
                    Glide.with(requireActivity()).load(divResultsDecimalsModels.get(position).getImageUrl())
                            .apply(new RequestOptions().centerCrop()).into(userImgView);

                    assert nicknameTextView != null;
                    nicknameTextView.setText(divResultsDecimalsModels.get(position).getNickname());

                    assert nicknameInfo != null;
                    nicknameInfo.setText(divResultsDecimalsModels.get(position).getNickname());

                    firebaseFirestore.collection("users").document(divResultsDecimalsModels.get(position).getId())
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
