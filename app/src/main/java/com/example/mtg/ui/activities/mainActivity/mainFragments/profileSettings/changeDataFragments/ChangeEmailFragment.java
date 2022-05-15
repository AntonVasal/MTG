package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
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


public class ChangeEmailFragment extends Fragment {
    private FragmentChangeDataBinding binding;
    private NavController navController;
    private ErrorDialog errorDialog;
    private ProfileSettingsViewModel profileSettingsViewModel;
    private static final String UPLOADING_FAILED = "uploading failed";
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
                    if (!aBoolean && Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeEmailFragment ){
                        navController.popBackStack(R.id.profileSettingsFragment, true);
                    }
                });
    }

    private void textChanged() {
        ValidationTextWatcher textWatcher = new ValidationTextWatcher(binding.changeEditText);
        binding.forChange.addTextChangedListener(textWatcher);
    }

    private void setViewData() {
        binding.forChange.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        binding.changeEditText.setHint(R.string.email);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_email_24);
        binding.changeButton.setText(R.string.change_email);
        binding.changeImage.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.ic_computer_wireframe, requireActivity().getTheme()));
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeEmailFragment) {
                navController.popBackStack();
            }
        });

        binding.changeButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.forChange.getText()).toString().trim();
            if (email.isEmpty()) {
                binding.changeEditText.setError(getResources().getString(R.string.email_is_required));
                binding.changeEditText.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.changeEditText.setError(getResources().getString(R.string.pls_provide_valid_email));
                binding.changeEditText.requestFocus();
                return;
            }
            binding.changeDataProgressBar.setVisibility(View.VISIBLE);

            profileSettingsViewModel.updateEmail(email, userField -> {
                switch (Objects.requireNonNull(userField.status)){
                    case SUCCESS:
                        binding.changeDataProgressBar.setVisibility(View.GONE);
                        navController.popBackStack(R.id.profileSettingsPasswordConfirmationFragment, true);
                        break;
                    case ERROR:
                        binding.changeDataProgressBar.setVisibility(View.GONE);
                        assert userField.message != null;
                        if (userField.message.equals(UPLOADING_FAILED)){
                            errorDialog.setMessage(getResources().getString(R.string.uploading_email_error_text));
                        }else {
                            errorDialog.setMessage(getResources().getString(R.string.update_email_error_text));
                        }
                        errorDialog.show();
                        break;
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        errorOccurBinding = null;
    }
}