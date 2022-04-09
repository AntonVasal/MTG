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

public class DecimalsFragment extends Fragment {

    private FragmentTypeTaskBinding binding;
    private Bundle bundle;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        bundle = new Bundle();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTypeTaskBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        binding.addText.setOnClickListener(view -> {
            bundle.putInt("typeNumber",3);
            bundle.putInt("taskType",1);
            navController.navigate(R.id.action_mainFragment2_to_countFragment,bundle);
        });
        binding.multiText.setOnClickListener(view ->{
            bundle.putInt("typeNumber",3);
            bundle.putInt("taskType",2);
            navController.navigate(R.id.action_mainFragment2_to_countFragment,bundle);
        });
        binding.subText.setOnClickListener(view -> {
            bundle.putInt("typeNumber",3);
            bundle.putInt("taskType",3);
            navController.navigate(R.id.action_mainFragment2_to_countFragment,bundle);
        });
        binding.divText.setOnClickListener(view -> {
            bundle.putInt("typeNumber",3);
            bundle.putInt("taskType",4);
            navController.navigate(R.id.action_mainFragment2_to_countFragment,bundle);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}