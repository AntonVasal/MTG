package com.example.mtg.ui.dialogs.apodDialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mtg.App;
import com.example.mtg.databinding.DialogBottomSheetApodBinding;
import com.example.mtg.models.apodModel.ApodModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ApodDialog extends BottomSheetDialog {

    private final DialogBottomSheetApodBinding binding;
    private ApodModel model;
    private ShimmerDrawable shimmerDrawable;

    public ApodDialog(@NonNull Context context, DialogBottomSheetApodBinding binding) {
        super(context);
        this.binding = binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.apodDialogAuthor.setSelected(true);
        binding.apodDialogTitle.setSelected(true);

        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.9f) //the alpha of the underlying children
                .setHighlightAlpha(0.8f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();

        // This is the placeholder for the imageView
        shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
    }

    public void loadData() {
        binding.apodDialogTitle.setText(Html.fromHtml(model.getTitle(),Html.FROM_HTML_MODE_LEGACY).toString());
        binding.apodDialogText.setText(Html.fromHtml(model.getExplanation(),Html.FROM_HTML_MODE_LEGACY).toString());
        if (model.getCopyright()!=null){
            binding.apodDialogAuthor.setText(Html.fromHtml(model.getCopyright(),Html.FROM_HTML_MODE_LEGACY).toString());
        }else{
            binding.apodDialogAuthor.setText("");
        }
        binding.apodDialogDate.setText(model.getDate());
        Glide.with(App.instance).load(model.getHdUrl())
                .placeholder(shimmerDrawable)
                .centerCrop().into(binding.apodDialogImage);
    }

    public void setModel(ApodModel model) {
        this.model = model;
    }
}
