package com.example.mtg.mainActivity.mainFragments.profileSettings.confirmPasswordFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileSettingsPasswordConfirmationBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileSettingsPasswordConfirmationFragment extends Fragment {

    private FragmentProfileSettingsPasswordConfirmationBinding binding;
    private String password;
    private String email;
    private NavController navController;
    private static final String TAG = "MainActivity";
    private static final String TYPE_FRAGMENTS = "typeFragments";
    private static final String USERS = "users";
    private static final String FAILED = "Failed";
    private int typeFragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        typeFragments = getArguments().getInt(TYPE_FRAGMENTS);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        new Thread(() -> firebaseFirestore.collection(USERS).document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .get().addOnSuccessListener(documentSnapshot -> {
                    UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                    assert userRegisterProfileModel != null;
                    email = userRegisterProfileModel.getEmail();
                })).start();
        initListeners();
        textChanged();
    }

    private void textChanged() {
        binding.passwordForConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.confirmPasswordEditText.getError()!=null){
                    binding.confirmPasswordEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }


    private void initListeners() {
        binding.confirmPasswordBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsPasswordConfirmationFragment) {
                navController.popBackStack();
            }
        });
        binding.confirmPasswordButton.setOnClickListener(view -> {
            password = Objects.requireNonNull(binding.passwordForConfirm.getText()).toString().trim();

            if (password.isEmpty()) {
                binding.confirmPasswordEditText.setError(getResources().getString(R.string.password_is_required));
                binding.confirmPasswordEditText.requestFocus();
                return;
            }

            if (password.length() < 6) {
                binding.confirmPasswordEditText.setError(getResources().getString(R.string.min_password));
                binding.confirmPasswordEditText.requestFocus();
                return;
            }

            binding.confirmPasswordProgressBar.setVisibility(View.VISIBLE);

            new Thread(() -> {
                AuthCredential authCredential = EmailAuthProvider.getCredential(email, password);
                Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                        .reauthenticate(authCredential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                NavDirections navDirections = null;
                                switch (typeFragments) {
                                    case 1:
                                        navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                                .actionProfileSettingsPasswordConfirmationFragmentToChangeNicknameFragment();
                                        break;
                                    case 2:
                                        navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                                .actionProfileSettingsPasswordConfirmationFragmentToChangeNameFragment();
                                        break;
                                    case 3:
                                        navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                                .actionProfileSettingsPasswordConfirmationFragmentToChangeSurnameFragment();
                                        break;
                                    case 4:
                                        navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                                .actionProfileSettingsPasswordConfirmationFragmentToChangeEmailFragment();
                                        break;
                                    case 5:
                                        navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                                .actionProfileSettingsPasswordConfirmationFragmentToChangeCountryFragment();
                                        break;
                                }
                                NavDirections finalNavDirections = navDirections;
                                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsPasswordConfirmationFragment) {
                                    requireActivity().runOnUiThread(() -> {
                                                binding.confirmPasswordProgressBar.setVisibility(View.GONE);
                                                assert finalNavDirections != null;
                                                binding.passwordForConfirm.setText("");
                                                navController.navigate(finalNavDirections);
                                            }
                                    );
                                }
                            } else {
                                requireActivity().runOnUiThread(() -> binding.confirmPasswordProgressBar.setVisibility(View.GONE));
                                Log.i(TAG, FAILED);
                                Toast.makeText(requireActivity(), FAILED, Toast.LENGTH_LONG).show();
                            }
                        });
            }).start();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileSettingsPasswordConfirmationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}