package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.profileSettingsFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.DialogErrorOccurBinding;
import com.example.mtg.databinding.FragmentProfileSettingsBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.profileSettingsFragment.profileSettingsFragmentViewModel.ProfileSettingsViewModel;
import com.example.mtg.ui.dialogs.serviceDialogs.ErrorDialog;
import com.example.mtg.utility.networkDetection.NetworkStateManager;

import java.util.Objects;


public class ProfileSettingsFragment extends Fragment {

    private FragmentProfileSettingsBinding binding;
    private NavController navController;
    private static final String TYPE_FRAGMENTS = "typeFragments";
    private static final String DATA = "DATA";
    private static final String LOADED = "loaded";
    private ErrorDialog errorDialog;
    private static int counter = 0;
    private Bundle bundle;
    private ProfileSettingsViewModel profileSettingsViewModel;
    private DialogErrorOccurBinding errorOccurBinding;
    private NetworkStateManager networkStateManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        bundle = new Bundle();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_settings, container, false);
        binding.setLifecycleOwner(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkStateManager = NetworkStateManager.getInstance();
        profileSettingsViewModel = new ViewModelProvider(requireActivity()).get(ProfileSettingsViewModel.class);
        errorOccurBinding = DialogErrorOccurBinding.inflate(getLayoutInflater());
        errorDialog = new ErrorDialog(requireActivity(),
                getResources().getString(R.string.loading_data_error_text),
                getResources().getString(R.string.updating_failed),
                errorOccurBinding);
        binding.setViewModel(profileSettingsViewModel);
        binding.infoNickname.setSelected(true);
        binding.infoName.setSelected(true);
        binding.infoSurname.setSelected(true);
        binding.infoEmail.setSelected(true);
        detectConnection();
        initListeners();
        getUserData();
    }

    private void detectConnection() {
        networkStateManager.getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean && Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsFragment) {
                        navController.popBackStack();
                    }
                });
    }

    private void getUserData() {
        profileSettingsViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            switch (user.status) {
                case SUCCESS:
                    Log.e(DATA, LOADED);
                    break;
                case ERROR:
                    errorDialog.show();
                    break;
            }
        });
    }

    private void initListeners() {
        binding.settingsBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsFragment) {
                navController.popBackStack();
            }
        });
        binding.changeNicknameButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS, 1);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeNameButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS, 2);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeSurnameButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS, 3);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeEmailButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS, 4);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeCountryButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS, 5);
            letsGoToConfirmPassword(bundle);
        });
        binding.changePasswordButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS, 6);
            letsGoToConfirmPassword(bundle);
        });
    }

    private void letsGoToConfirmPassword(Bundle bundle) {
        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsFragment) {
            navController.navigate(R.id.action_profileSettingsFragment_to_profileSettingsPasswordConfirmationFragment, bundle);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        profileSettingsViewModel.removeListener();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Boolean isConnect = networkStateManager.getNetworkConnectivityStatus().getValue();
        if (isConnect != null && isConnect && counter>0){
            profileSettingsViewModel.loadData();
        }
        counter++;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        errorOccurBinding = null;
    }
}