package com.example.mtg.bindingAdapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class LoadApodImageBindingAdapter {
    @BindingAdapter("apodImage")
    public static void apodImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
