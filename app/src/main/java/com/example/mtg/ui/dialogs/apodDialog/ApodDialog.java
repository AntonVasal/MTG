package com.example.mtg.ui.dialogs.apodDialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mtg.App;
import com.example.mtg.databinding.DialogBottomSheetApodBinding;
import com.example.mtg.models.apodModel.ApodModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ApodDialog extends BottomSheetDialog {

    private final DialogBottomSheetApodBinding binding;
    private ApodModel model;

    public ApodDialog(@NonNull Context context, DialogBottomSheetApodBinding binding) {
        super(context);
        this.binding = binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }

    public void loadData() {
        binding.apodDialogTitle.setText(model.getTitle());
        binding.apodDialogText.setText(model.getExplanation());
        if (model.getCopyright()!=null){
            binding.apodDialogAuthor.setText(model.getCopyright());
        }
        binding.apodDialogDate.setText(model.getDate());
        Glide.with(App.instance).load(model.getHdUrl()).centerCrop().into(binding.apodDialogImage);
    }

    public void setModel(ApodModel model) {
        this.model = model;
    }
}
