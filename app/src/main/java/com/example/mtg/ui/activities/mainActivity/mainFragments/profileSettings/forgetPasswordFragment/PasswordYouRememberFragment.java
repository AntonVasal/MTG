package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.forgetPasswordFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class PasswordYouRememberFragment extends Fragment {

    private NavController navController;
    private FragmentChangeDataBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String password;
    private static final String USERS = "users";
    private static final String TAG = "MainActivity";
    private static final String FAILED = "Failed";
    private static final String SUCCESS = "Success";
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        setViewsData();
        initListeners();
        textChanged();
        new Thread(() -> firebaseFirestore.collection(USERS).document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .get().addOnSuccessListener(documentSnapshot -> {
                    UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                    assert userRegisterProfileModel != null;
                    email = userRegisterProfileModel.getEmail();
                })).start();
    }

    private void textChanged() {
        binding.forChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.changeEditText.getError()!=null){
                    binding.changeEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.passwordYouRememberFragment) {
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
                if(email.isEmpty()){
                    Log.i(TAG, FAILED);
                }else {
                    binding.changeDataProgressBar.setVisibility(View.VISIBLE);
                    new Thread(() -> firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            requireActivity().runOnUiThread(() -> {
                                binding.changeDataProgressBar.setVisibility(View.GONE);
                                Log.i(TAG,SUCCESS);
                            });
                        }else{
                            requireActivity().runOnUiThread(() -> binding.changeDataProgressBar.setVisibility(View.GONE));
                        }
                    })).start();
                }
        });
    }

    private void setViewsData() {
        binding.changeEditText.setHint(R.string.password_you_remember);
        binding.changeImage.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.ic_password_forgot,requireActivity().getTheme()));
        binding.forChange.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_security_24);
        binding.changeEditText.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        binding.changeButton.setText(R.string.confirm);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
