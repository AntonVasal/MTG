package com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodAdapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtg.databinding.ItemApodRecyclerBinding;
import com.example.mtg.models.apodModel.ApodModel;

import java.util.ArrayList;

public class ApodRecyclerAdapter extends RecyclerView.Adapter<ApodRecyclerViewHolder> {

    private final ArrayList<ApodModel> arrayList;
    private final Context context;
    private final ApodRecyclerOnItemClickInterface recyclerOnItemClickInterface;

    public ApodRecyclerAdapter(ArrayList<ApodModel> arrayList, Context context, ApodRecyclerOnItemClickInterface recyclerOnItemClickInterface) {
        this.arrayList = arrayList;
        this.context = context;
        this.recyclerOnItemClickInterface = recyclerOnItemClickInterface;
    }

    @NonNull
    @Override
    public ApodRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemApodRecyclerBinding binding = ItemApodRecyclerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ApodRecyclerViewHolder(binding, recyclerOnItemClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ApodRecyclerViewHolder holder, int position) {
        holder.binding.apodItemAuthor.setText(arrayList.get(position).getCopyright());
        holder.binding.apodItemDate.setText(arrayList.get(position).getDate());
        holder.binding.apodItemTitle.setText(arrayList.get(position).getTitle());
        Glide.with(context).load(arrayList.get(position).getHdUrl()).centerCrop().into(holder.binding.apodItemImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
