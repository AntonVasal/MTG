package com.example.mtg.mainActivity.mainFragments.results.resultsDialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.databinding.BottomSheetResultsDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ResultsDialog extends BottomSheetDialog {
    private final Context context;
    private final BottomSheetResultsDialogBinding binding;
    private final String name;
    private final String nickname;
    private final String imageUrl;
    private final String country;
    private final int score;
    private final int tasks;

    public ResultsDialog(@NonNull Context context,BottomSheetResultsDialogBinding binding, String name, String nickname, String imageUrl, String country, int score, int tasks ) {
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

        loadData(name,nickname,imageUrl,country,score,tasks);
    }


    public void loadData(String name, String nickname, String imageUrl, String country, int score, int tasks ) {
        binding.infoScoreDialog.setText(String.valueOf(score));
        binding.infoTasksDialog.setText(String.valueOf(tasks));
        binding.nicknameTextDialog.setText(nickname);
        binding.infoNicknameDialog.setText(nickname);
        binding.infoNameDialog.setText(name);
        binding.infoCountryDialog.setText(country);
        Glide.with(context).load(imageUrl).apply(new RequestOptions().centerCrop()).into(binding.dialogImage);
    }
}

