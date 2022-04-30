package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.core.ValidationTextWatcher;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ChangePasswordFragment extends Fragment {

    private FragmentChangeDataBinding binding;
    private NavController navController;
    private String password;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    private static final String FAILED = "Failed";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewData();
        initListeners();
        textChanged();
    }

    private void textChanged() {
        ValidationTextWatcher textWatcher = new ValidationTextWatcher(binding.changeEditText);
        binding.forChange.addTextChangedListener(textWatcher);
    }

    private void setViewData() {
        binding.changeEditText.setHint(R.string.new_password);
        binding.forChange.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_security_24);
        binding.changeEditText.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        binding.changeButton.setText(R.string.change_password);
        binding.changeImage.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.ic_key_password,requireActivity().getTheme()));
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changePasswordFragment){
                navController.popBackStack();
            }
        });
        binding.changeButton.setOnClickListener(view -> {
            password = Objects.requireNonNull(binding.forChange.getText()).toString().trim();
            if (password.isEmpty()){
                binding.changeEditText.setError(getResources().getString(R.string.password_is_required));
                binding.changeEditText.requestFocus();
                return;
            }
            if (password.length()<6){
                binding.changeEditText.setError(getResources().getString(R.string.min_password));
                binding.changeEditText.requestFocus();
                return;
            }
            binding.changeDataProgressBar.setVisibility(View.VISIBLE);

            new Thread(() -> Objects.requireNonNull(firebaseAuth.getCurrentUser()).updatePassword(password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            requireActivity().runOnUiThread(() -> {
                                binding.changeDataProgressBar.setVisibility(View.GONE);
                                navController.popBackStack(R.id.profileSettingsPasswordConfirmationFragment,true);
                            });
                        } else {
                            Log.i(TAG, FAILED);
                            requireActivity().runOnUiThread(() -> binding.changeDataProgressBar.setVisibility(View.GONE));
                        }
                    })).start();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
