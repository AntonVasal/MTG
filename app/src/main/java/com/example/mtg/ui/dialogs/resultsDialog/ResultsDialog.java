package com.example.mtg.ui.dialogs.resultsDialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.R;
import com.example.mtg.databinding.DialogBottomSheetResultsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ResultsDialog extends BottomSheetDialog {

    private final Context context;
    private final DialogBottomSheetResultsBinding binding;
    private String name;
    private String nickname;
    private String imageUrl;
    private String country;
    private int score;
    private int tasks;

    public ResultsDialog(@NonNull Context context, DialogBottomSheetResultsBinding binding, String name, String nickname, String imageUrl, String country, int score, int tasks ) {
        super(context);
        this.context = context;
        this.binding = binding;
        this.name = name;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.country = country;
        this.score = score;
        this.tasks = tasks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.exitButtonBottomDialog.setOnClickListener(view -> dismiss());
        binding.nicknameTextDialog.setSelected(true);
        binding.infoNicknameDialog.setSelected(true);
        binding.infoNameDialog.setSelected(true);
        setDataInViews();
    }


    public void loadData(String name, String nickname, String imageUrl, String country, int score, int tasks ) {
        this.name = name;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.country = country;
        this.score = score;
        this.tasks = tasks;
    }

    public void setDataInViews(){
        binding.infoScoreDialog.setText(String.valueOf(score));
        binding.infoTasksDialog.setText(String.valueOf(tasks));
        binding.nicknameTextDialog.setText(nickname);
        binding.infoNicknameDialog.setText(nickname);
        binding.infoNameDialog.setText(name);
        binding.infoCountryDialog.setText(country);
        Glide.with(context).load(imageUrl)
                .placeholder(R.drawable.ic_user_main)
                .error(R.drawable.ic_user_main)
                .apply(new RequestOptions().centerCrop()).into(binding.dialogImage);
    }



}

