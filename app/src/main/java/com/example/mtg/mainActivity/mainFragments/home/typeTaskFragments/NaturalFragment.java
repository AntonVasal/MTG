package com.example.mtg.mainActivity.mainFragments.home.typeTaskFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentTypeTaskBinding;

import java.util.Objects;


public class NaturalFragment extends Fragment {
    private FragmentTypeTaskBinding binding;
    private Bundle bundle;
    private NavController navController;
    private static final String TYPE_NUMBER = "typeNumber";
    private static final String TASK_TYPE = "taskType";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        bundle = new Bundle();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTypeTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }



    private void initListeners() {
        binding.addText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 1);
            bundle.putInt(TASK_TYPE, 1);
            letsCount();
        });
        binding.multiText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 1);
            bundle.putInt(TASK_TYPE, 2);
            letsCount();
        });
        binding.subText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 1);
            bundle.putInt(TASK_TYPE, 3);
            letsCount();
        });
        binding.divText.setOnClickListener(view -> {
            bundle.putInt(TYPE_NUMBER, 1);
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}