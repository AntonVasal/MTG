package com.example.mtg.MainFragments.Results.Adapters.ResultsRecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtg.MainFragments.Results.Models.UserResultsModel;
import com.example.mtg.R;

import java.util.List;

public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<ResultsRecyclerViewHolder> {
    List<UserResultsModel> itemList;
    Context mContext;

    public ResultsRecyclerViewAdapter(List<UserResultsModel> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ResultsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_for_results,parent,false);
        return new ResultsRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsRecyclerViewHolder holder, int position) {
        Glide.with(mContext)
                .load(itemList.get(position).getImage())
                .into(holder.userImg);
        holder.userName.setText(itemList.get(position).getName());
        holder.userScore.setText(itemList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
