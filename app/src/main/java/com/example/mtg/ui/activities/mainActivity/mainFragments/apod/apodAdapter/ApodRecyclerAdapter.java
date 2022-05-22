package com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtg.R;
import com.example.mtg.databinding.ItemApodRecyclerBinding;
import com.example.mtg.models.apodModel.ApodModel;

import java.util.ArrayList;

public class ApodRecyclerAdapter extends RecyclerView.Adapter<ApodRecyclerViewHolder> {

    private ArrayList<ApodModel> arrayList;
    private final Context context;
    private final ApodRecyclerOnItemClickInterface recyclerOnItemClickInterface;

    public ApodRecyclerAdapter(ArrayList<ApodModel> arrayList, Context context, ApodRecyclerOnItemClickInterface recyclerOnItemClickInterface) {
        this.arrayList = arrayList;
        this.context = context;
        this.recyclerOnItemClickInterface = recyclerOnItemClickInterface;
    }

    public void setArrayList(ArrayList<ApodModel> arrayList) {
        if (this.arrayList == null) {
            this.arrayList = arrayList;
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ApodRecyclerAdapter.this.arrayList.size();
                }

                @Override
                public int getNewListSize() {
                    return arrayList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ApodRecyclerAdapter.this.arrayList.get(oldItemPosition).getDate().equals(arrayList.get(newItemPosition).getDate());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ApodModel newApods = ApodRecyclerAdapter.this.arrayList.get(oldItemPosition);

                    ApodModel oldApods = arrayList.get(newItemPosition);

                    return newApods.getDate().equals(oldApods.getDate());
                }
            });
            this.arrayList = arrayList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ApodRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemApodRecyclerBinding binding = ItemApodRecyclerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ApodRecyclerViewHolder(binding, recyclerOnItemClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ApodRecyclerViewHolder holder, int position) {
        holder.binding.apodItemCard.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_for_recycler));
        holder.binding.setApodModel(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
