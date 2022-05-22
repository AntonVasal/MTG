package com.example.mtg.bindingAdapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class LoadUsersImageBindingAdapter {
    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().override(170, 170))
                .into(view);
    }
}
