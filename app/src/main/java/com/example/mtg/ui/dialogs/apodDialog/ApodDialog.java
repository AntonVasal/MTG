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

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

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
        String regex = "[\\p{InCombiningDiacriticalMarks}]+";

        String title = Normalizer.normalize(model.getTitle(), Normalizer.Form.NFKD);
        String s2 = new String(title.replaceAll(regex, "").getBytes(StandardCharsets.US_ASCII), StandardCharsets.US_ASCII);
        binding.apodDialogTitle.setText(s2);

        String explanation = Normalizer.normalize(model.getExplanation(), Normalizer.Form.NFKD);
        String a2 = new String(explanation.replaceAll(regex, "").getBytes(StandardCharsets.US_ASCII), StandardCharsets.US_ASCII);
        binding.apodDialogText.setText(a2);

        if (model.getCopyright()!=null){
            String copyright = Normalizer.normalize(model.getCopyright(), Normalizer.Form.NFKD);
            String b2 = new String(copyright.replaceAll(regex, "").getBytes(StandardCharsets.US_ASCII), StandardCharsets.US_ASCII);
            binding.apodDialogAuthor.setText(b2);
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
