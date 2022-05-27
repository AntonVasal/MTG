package com.example.mtg.bindingAdapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.R;

public class LoadUsersImageBindingAdapter {
    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("")){
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions().override(170, 170))
                    .placeholder(R.drawable.ic_user_main)
                    .error(R.drawable.ic_user_main)
                    .into(view);
        }
    }
}
