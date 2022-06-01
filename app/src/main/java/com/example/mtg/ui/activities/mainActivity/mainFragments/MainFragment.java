package com.example.mtg.ui.activities.mainActivity.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.mtg.R;
import com.example.mtg.databinding.DialogConnectionBinding;
import com.example.mtg.databinding.FragmentMainBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.apod.ApodFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.HomeFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profile.ProfileFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.ResultFragment;
import com.example.mtg.ui.dialogs.connectionDialog.ConnectionDialog;
import com.example.mtg.utility.networkDetection.NetworkStateManager;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private FragmentManager fragmentManager;
    private DialogConnectionBinding dialogConnectionBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater,container,false);
        dialogConnectionBinding = DialogConnectionBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = requireActivity().getSupportFragmentManager();

        binding.mainBottomNavigation.add(new MeowBottomNavigation.Model(0,R.drawable.ic_baseline_home_24));
        binding.mainBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_emoji_events_24));
        binding.mainBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_image_24));
        binding.mainBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_person_24));

        binding.mainBottomNavigation.show(0,true);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new ResultFragment());
        fragmentArrayList.add(new ApodFragment());
        fragmentArrayList.add(new ProfileFragment());

        observeConnection();

        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragmentArrayList.get(0)).commit();

        binding.mainBottomNavigation.setOnClickMenuListener(model -> {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragmentArrayList.get(model.getId())).commit();
            return null;
        });
    }

    private void observeConnection() {
        ConnectionDialog connectionDialog = new ConnectionDialog(requireContext(),dialogConnectionBinding,getResources().getString(R.string.connection_lost_in_main));

        binding.fabInternet.setOnClickListener(view -> connectionDialog.show());
        NetworkStateManager.getInstance().getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean){
                        binding.fabInternet.setVisibility(View.VISIBLE);
                        if (this.isAdded()){
                            connectionDialog.show();
                            Window window = connectionDialog.getWindow();
                            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        }
                    }else{
                        binding.fabInternet.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        dialogConnectionBinding = null;
    }
}
