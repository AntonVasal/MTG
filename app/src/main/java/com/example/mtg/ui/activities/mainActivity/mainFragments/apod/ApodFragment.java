package com.example.mtg.ui.activities.mainActivity.mainFragments.apod;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.mtg.R;
import com.example.mtg.databinding.DialogBottomSheetApodBinding;
import com.example.mtg.databinding.FragmentApodBinding;
import com.example.mtg.models.apodModel.ApodModel;
import com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodAdapter.ApodRecyclerAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodAdapter.ApodRecyclerOnItemClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodViewModel.ApodViewModel;
import com.example.mtg.ui.dialogs.apodDialog.ApodDialog;

import java.util.ArrayList;
import java.util.Locale;

public class ApodFragment extends Fragment implements ApodRecyclerOnItemClickInterface {
    private FragmentApodBinding binding;
    private ApodViewModel apodViewModel;
    private ArrayList<ApodModel> apodModels = new ArrayList<>();
    private ApodRecyclerAdapter adapter;
    private DialogBottomSheetApodBinding bottomSheetApodBinding;
    private ApodDialog apodDialog;
    private ArrayList<ApodModel> listForItemClick;

    @Override
    public void onStart() {
        super.onStart();
        MeowBottomNavigation meowBottomNavigation = requireActivity().findViewById(R.id.main_bottom_navigation);
        if(meowBottomNavigation.getDefaultIconColor() != R.color.blue){
            meowBottomNavigation.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.gradient_for_bottom_navigation));
            meowBottomNavigation.setDefaultIconColor(Color.parseColor("#112CBF"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentApodBinding.inflate(inflater, container, false);
        bottomSheetApodBinding = DialogBottomSheetApodBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apodViewModel = new ViewModelProvider(requireActivity()).get(ApodViewModel.class);
        apodDialog = new ApodDialog(requireContext(), bottomSheetApodBinding);
        adapter = new ApodRecyclerAdapter(new ArrayList<>(), requireContext(), this);
        binding.apodRecycler.setAdapter(adapter);
        binding.apodRecyclerProgressBar.setVisibility(View.VISIBLE);
        setData();
//        detectConnection();
    }

    private void setSearch() {
        binding.apodSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sortApodLists(s.trim());
                return false;
            }
        });
    }

//    private void detectConnection() {
//        NetworkStateManager.getInstance().getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
//                aBoolean -> {
//                    if ((binding.apodError.getVisibility() == View.VISIBLE) && aBoolean ) {
//                        apodViewModel.loadData();
//                    }
//                });
//    }

    private void sortApodLists(String s) {
        ArrayList<ApodModel> sortedList = new ArrayList<>();
        ApodModel apodModel;
        String date;
        String title;
        for (int i = 0; i < apodModels.size(); i++) {
            apodModel = apodModels.get(i);
            date = apodModel.getDate().toLowerCase(Locale.ROOT).trim();
            title = apodModel.getTitle().toLowerCase(Locale.ROOT).trim();
            if (title.contains(s) || date.contains(s)) {
                sortedList.add(apodModel);
            }
        }
        if (!sortedList.isEmpty()) {
            adapter.setArrayList(sortedList);
        } else {
            adapter.setArrayList(new ArrayList<>());
        }
        listForItemClick = sortedList;
    }

    private void setData() {
        apodViewModel.getApodList().observe(getViewLifecycleOwner(), data -> {
            switch (data.status) {
                case SUCCESS:
                    apodModels = data.data;
                    listForItemClick = apodModels;
                    adapter.setArrayList(apodModels);
                    binding.apodRecyclerProgressBar.setVisibility(View.GONE);
                    binding.apodError.setVisibility(View.GONE);
                    setSearch();
                    break;
                case ERROR:
                    binding.apodRecyclerProgressBar.setVisibility(View.GONE);
                    binding.apodError.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

//    private MaterialDatePicker<Long> materialDatePicker;
//    MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
//        materialDateBuilder.setTitleText("SELECT A DATE");
//    materialDatePicker = materialDateBuilder.build();
// materialDatePicker.addOnPositiveButtonClickListener(this::getDate);
//    private void getDate(Long selection){
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        calendar.setTimeInMillis(selection);
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        String formattedDate  = format.format(calendar.getTime());
//        binding.description.setText(formattedDate);
//    }


    @Override
    public void onApodRecyclerItemClick(int position) {
        apodDialog.setModel(listForItemClick.get(position));
        apodDialog.loadData();
        apodDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bottomSheetApodBinding = null;
        binding = null;
    }
}