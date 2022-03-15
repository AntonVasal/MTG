package com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtg.R;

public class ResultsRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView userName,userScore;
    ImageView userImg;

    public ResultsRecyclerViewHolder(@NonNull View itemView, OnItemResultsRecyclerClickInterface onItemResultsRecyclerClickInterface,int typeNumber) {
        super(itemView);

        userName = itemView.findViewById(R.id.user_results_name);
        userScore = itemView.findViewById(R.id.user_score);
        userImg = itemView.findViewById(R.id.user_results_img);

        itemView.setOnClickListener(view -> {
            if(onItemResultsRecyclerClickInterface != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    onItemResultsRecyclerClickInterface.onItemClick(position,typeNumber);
                }
            }
        });
    }
}
