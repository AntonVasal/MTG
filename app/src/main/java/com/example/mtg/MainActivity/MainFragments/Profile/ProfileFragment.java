package com.example.mtg.MainActivity.MainFragments.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mtg.LogActivity.LogActivity;
import com.example.mtg.LogActivity.Models.UserRegisterProfileModel;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
    private FirebaseFirestore mFirebaseFirestore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        binding.userProfileImage.setImageResource(R.drawable.ic_baseline_person_150);
        binding.profileProgressBar.setVisibility(View.VISIBLE);
        setData();
        initListeners();
        return view;
    }

    private void setData() {
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mFirebaseFirestore.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserRegisterProfileModel user = documentSnapshot.toObject(UserRegisterProfileModel.class);
                    assert user != null;
                    binding.userNickname.setText(user.getNickname());
                    binding.userEmail.setText(user.getEmail());
                    binding.infoCountry.setText(user.getCountry());
                    binding.infoEmail.setText(user.getEmail());
                    binding.infoName.setText(user.getName());
                    binding.infoSurname.setText(user.getSurname());
                    binding.infoNickname.setText(user.getNickname());
                    binding.profileProgressBar.setVisibility(View.GONE);
                });

    }

    private void initListeners() {
        binding.logOutButton.setOnClickListener(view1 -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LogActivity.class));
            requireActivity().finish();
        });
    }
}