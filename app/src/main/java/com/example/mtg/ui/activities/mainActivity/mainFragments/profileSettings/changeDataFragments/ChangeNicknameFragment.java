package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.core.textwatchers.ValidationTextWatcher;
import com.example.mtg.databinding.DialogErrorOccurBinding;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.profileSettingsFragment.profileSettingsFragmentViewModel.ProfileSettingsViewModel;
import com.example.mtg.ui.dialogs.serviceDialogs.ErrorDialog;
import com.example.mtg.utility.networkDetection.NetworkStateManager;

import java.util.Objects;


public class ChangeNicknameFragment extends Fragment {
    private FragmentChangeDataBinding binding;
    private NavController navController;
    private ProfileSettingsViewModel profileSettingsViewModel;
    private ErrorDialog errorDialog;
    private DialogErrorOccurBinding errorOccurBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileSettingsViewModel = new ViewModelProvider(requireActivity()).get(ProfileSettingsViewModel.class);
        errorOccurBinding = DialogErrorOccurBinding.inflate(getLayoutInflater());
        errorDialog = new ErrorDialog(requireActivity(),
                getResources().getString(R.string.update_error_text),
                getResources().getString(R.string.updating_failed),
                errorOccurBinding);
        detectConnection();
        initListeners();
        setViewData();
        textChanged();
    }

    private void detectConnection() {
        NetworkStateManager.getInstance().getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean && Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeNicknameFragment) {
                        navController.popBackStack(R.id.profileSettingsFragment, true);
                    }
                });
    }

    private void textChanged() {
        ValidationTextWatcher textWatcher = new ValidationTextWatcher(binding.changeEditText);
        binding.forChange.addTextChangedListener(textWatcher);
    }

    private void setViewData() {
        binding.changeEditText.setHint(R.string.nickname);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_star_24);
        binding.changeButton.setText(R.string.change_nickname);
        binding.changeImage.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.ic_diamond_jewel, requireActivity().getTheme()));
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeNicknameFragment) {
                navController.popBackStack();
            }
        });
        binding.changeButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(binding.forChange.getText()).toString().trim().isEmpty()) {
                binding.changeEditText.setError(getResources().getString(R.string.nickname_is_required));
                binding.changeEditText.requestFocus();
                return;
            }
            binding.changeDataProgressBar.setVisibility(View.VISIBLE);
            String nickname = binding.forChange.getText().toString().trim();
            sendNicknameToFirestore(nickname);
        });
    }

    private void sendNicknameToFirestore(String nickname) {
        profileSettingsViewModel.updateNickname(nickname, userField -> {
           switch (userField.status){
               case SUCCESS:
                   if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeNicknameFragment) {
                       binding.changeDataProgressBar.setVisibility(View.GONE);
                       navController.popBackStack(R.id.profileSettingsPasswordConfirmationFragment, true);
                   }
                   break;
               case ERROR:
                   binding.changeDataProgressBar.setVisibility(View.GONE);
                   if (userField.data!= null){
                       errorDialog.setMessage(getResources().getString(R.string.uploading_nickname_error_text));
                   }else{
                       errorDialog.setMessage(getResources().getString(R.string.update_error_text));
                   }
                   errorDialog.show();
                   break;
           }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        errorOccurBinding = null;
    }
}