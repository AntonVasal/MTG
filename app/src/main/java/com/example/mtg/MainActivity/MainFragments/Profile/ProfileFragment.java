package com.example.mtg.MainActivity.MainFragments.Profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mtg.LogActivity.LogActivity;
import com.example.mtg.MainActivity.MainFragments.Profile.ViewModel.ProfileViewModel;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
//    private FirebaseFirestore mFirebaseFirestore;
    private ProfileViewModel profileViewModel;


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
//        mFirebaseFirestore = FirebaseFirestore.getInstance();
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);


        binding.userProfileImage.setImageResource(R.drawable.ic_baseline_person_150);
        binding.profileProgressBar.setVisibility(View.VISIBLE);

        setData();
        initListeners();



        return view;
    }

    private void setData() {
        profileViewModel.getUser().observe(requireActivity(), userRegisterProfileModel -> {
            binding.userNickname.setText(userRegisterProfileModel.getNickname());
            binding.userEmail.setText(userRegisterProfileModel.getEmail());
            binding.infoCountry.setText(userRegisterProfileModel.getCountry());
            binding.infoEmail.setText(userRegisterProfileModel.getEmail());
            binding.infoName.setText(userRegisterProfileModel.getName());
            binding.infoSurname.setText(userRegisterProfileModel.getSurname());
            binding.infoNickname.setText(userRegisterProfileModel.getNickname());
            binding.profileProgressBar.setVisibility(View.GONE);
        });


//        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//        mFirebaseFirestore.collection("users").document(userId).get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    UserRegisterProfileModel user = documentSnapshot.toObject(UserRegisterProfileModel.class);
//                    assert user != null;
//
////                    binding.userNickname.setText(user.getNickname());
////                    binding.userEmail.setText(user.getEmail());
////                    binding.infoCountry.setText(user.getCountry());
////                    binding.infoEmail.setText(user.getEmail());
////                    binding.infoName.setText(user.getName());
////                    binding.infoSurname.setText(user.getSurname());
////                    binding.infoNickname.setText(user.getNickname());
////                    binding.profileProgressBar.setVisibility(View.GONE);
//                });

    }

    private void initListeners() {
        binding.logOutButton.setOnClickListener(view1 -> showExitDialog());
    }

    private void showExitDialog() {
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.exit_dialog);

        MaterialButton cancel = dialog.findViewById(R.id.cancel_dialog_button);
        cancel.setOnClickListener(view -> dialog.dismiss());

        MaterialButton exit = dialog.findViewById(R.id.exit_dialog_button);
        exit.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LogActivity.class));
            requireActivity().finish();
        });

        dialog.show();
    }

}