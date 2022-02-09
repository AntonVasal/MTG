package com.example.mtg.MainFragments.Results.Adapters.ResultsRecyclerAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtg.R;

public class ResultsRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView userName,userScore;
    ImageView userImg;

    public ResultsRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.user_results_name);
        userScore = itemView.findViewById(R.id.user_score);
        userImg = itemView.findViewById(R.id.user_results_img);
    }
}
