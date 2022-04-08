package com.example.mtg.mainActivity.mainFragments.home.typeTaskFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentTypeTaskBinding;
import com.example.mtg.mainActivity.count.CountFragment;

public class DecimalsFragment extends Fragment {

    private FragmentTypeTaskBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        binding.addText.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.fragment_container_view,new CountFragment(1,3))
                .addToBackStack("")
                .commit());
        binding.multiText.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.fragment_container_view,new CountFragment(2,3))
                .addToBackStack("")
                .commit());
        binding.subText.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.fragment_container_view,new CountFragment(3,3))
                .addToBackStack("")
                .commit());
        binding.divText.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.fragment_container_view,new CountFragment(4,3))
                .addToBackStack("")
                .commit());
    }
}