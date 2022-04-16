package com.example.mtg.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ChangeSurnameFragment extends Fragment {
    private FragmentChangeDataBinding binding;
    private NavController navController;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String USERS = "users";

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
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        setViewData();
        textChanged();
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


    private void setViewData() {
        binding.changeEditText.setHint(R.string.surname);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_group_24);
        binding.changeButton.setText(R.string.change_surname);
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeSurnameFragment) {
                navController.popBackStack();
            }
        });
        binding.changeButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(binding.forChange.getText()).toString().trim().isEmpty()) {
                binding.changeEditText.setError(getResources().getString(R.string.surname_is_required));
                binding.changeEditText.requestFocus();
                return;
            }
            binding.changeDataProgressBar.setVisibility(View.VISIBLE);
            String surname = binding.forChange.getText().toString().trim();
            String id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                    .get().addOnSuccessListener(documentSnapshot -> {
                        UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                        assert userRegisterProfileModel != null;
                        userRegisterProfileModel.setSurname(surname);
                        firebaseFirestore.collection(USERS).document(id)
                                .set(userRegisterProfileModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeSurnameFragment) {
                                    requireActivity().runOnUiThread(() -> {
                                        binding.changeDataProgressBar.setVisibility(View.GONE);
                                        navController.popBackStack(R.id.profileSettingsPasswordConfirmationFragment,true);
                                    });
                                }
                            } else {
                                requireActivity().runOnUiThread(() -> binding.changeDataProgressBar.setVisibility(View.GONE));
                                Log.i(TAG, FAILED);
                            }
                        });
                    })).start();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}