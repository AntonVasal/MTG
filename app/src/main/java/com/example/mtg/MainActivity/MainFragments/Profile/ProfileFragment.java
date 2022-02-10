package com.example.mtg.MainActivity.MainFragments.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mtg.LogActivity.LogActivity;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        Glide.with(getContext()).load("")
                .into(binding.userProfileImage);
        setData();
        initListeners();
        return view;
    }

    private void setData() {

    }

    private void initListeners() {
        binding.logOutButton.setOnClickListener(view1 -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LogActivity.class));
            getActivity().finish();
        });
    }
}