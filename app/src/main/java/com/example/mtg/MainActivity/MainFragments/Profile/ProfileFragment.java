package com.example.mtg.MainActivity.MainFragments.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mtg.R;

public class ProfileFragment extends Fragment {

    ImageView userMainImg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userMainImg = view.findViewById(R.id.user_profile_image);
        Glide.with(getContext()).load("")
                .into(userMainImg);
        return view;
    }
}