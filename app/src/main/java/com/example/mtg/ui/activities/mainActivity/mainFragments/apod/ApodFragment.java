package com.example.mtg.ui.activities.mainActivity.mainFragments.apod;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mtg.databinding.FragmentApodBinding;
import com.example.mtg.models.apodModel.ApodModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodAdapter.ApodRecyclerAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodAdapter.ApodRecyclerOnItemClickInterface;
import com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodViewModel.ApodViewModel;

import java.util.ArrayList;

public class ApodFragment extends Fragment implements ApodRecyclerOnItemClickInterface {
    private FragmentApodBinding binding;
    private ApodViewModel apodViewModel;
    private ArrayList<ApodModel> apodModels = new ArrayList<>();
    private ApodRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentApodBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apodViewModel = new ViewModelProvider(requireActivity()).get(ApodViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.apodRecycler.setLayoutManager(layoutManager);
        binding.apodRecyclerProgressBar.setVisibility(View.VISIBLE);
        setData();
    }

    private void setData() {
        apodViewModel.getApodList("apod?api_key=DEMO_KEY").observe(getViewLifecycleOwner(), data -> {
            switch (data.status){
                case SUCCESS:
                    apodModels = data.data;
                    adapter = new ApodRecyclerAdapter(apodModels, requireContext(),this);
                    binding.apodRecycler.setAdapter(adapter);
                    binding.apodRecyclerProgressBar.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.apodRecyclerProgressBar.setVisibility(View.GONE);
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

    }
}
