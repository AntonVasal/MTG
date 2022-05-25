package com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.java;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.FragmentTypeTaskBinding;

import java.util.Objects;


public class IntegerFragment extends BaseBindingFragment<FragmentTypeTaskBinding> {
    private Bundle bundle;
    private NavController navController;
    private static final String TYPE_NUMBER = "typeNumber";
    private static final String TASK_TYPE = "taskType";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = new Bundle();
        initListeners();
        setImages();
    }

    private void setImages() {
//        binding.helloCard.setCardBackgroundColor(requireActivity().getResources().getColor(R.color.count_green,requireActivity().getTheme()));
//        binding.addText.setImageResource(R.drawable.ic_plus_green);
//        binding.subText.setImageResource(R.drawable.ic_minus_green);
//        binding.divText.setImageResource(R.drawable.ic_div_green);
//        binding.countLayout.setBackgroundColor(requireActivity().getResources().getColor(R.color.count_green,requireActivity().getTheme()));
//        binding.multiText.setImageResource(R.drawable.ic_multi_green);
    }

    private void initListeners() {
        binding.addText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 2);
            bundle.putInt(TASK_TYPE, 1);
            letsCount();
        });
        binding.multiText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 2);
            bundle.putInt(TASK_TYPE, 2);
            letsCount();
        });
        binding.subText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 2);
            bundle.putInt(TASK_TYPE, 3);
            letsCount();
        });
        binding.divText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 2);
            bundle.putInt(TASK_TYPE, 4);
            letsCount();
        });
    }

    private void letsCount(){
        if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.mainFragment2){
            navController.navigate(R.id.action_mainFragment2_to_countFragment, bundle);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type_task;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}