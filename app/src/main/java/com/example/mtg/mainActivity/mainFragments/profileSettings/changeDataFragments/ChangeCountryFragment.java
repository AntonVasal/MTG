package com.example.mtg.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
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


public class ChangeCountryFragment extends Fragment {
    private FragmentChangeDataBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private NavController navController;
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
        binding = FragmentChangeDataBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        initListeners();
        setViewData();
    }

    private void setViewData() {
        binding.forChange.setVisibility(View.INVISIBLE);
        binding.changeEditText.setVisibility(View.INVISIBLE);
        binding.changeButton.setText(R.string.change_country);
        binding.countryPickerForChange.setVisibility(View.VISIBLE);
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> navController.popBackStack());
        binding.changeButton.setOnClickListener(view -> {
            String country = binding.countryPickerForChange.getSelectedCountryName();
            new Thread(() -> firebaseFirestore.collection(USERS)
                    .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .get().addOnSuccessListener(documentSnapshot -> {

                UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                assert userRegisterProfileModel != null;
                userRegisterProfileModel.setCountry(country);

                firebaseFirestore.collection(USERS)
                        .document(firebaseAuth.getCurrentUser().getUid())
                        .set(userRegisterProfileModel)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                                navController.popBackStack();
                            } else {
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