package com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mtg.databinding.ItemApodRecyclerBinding;

public class ApodRecyclerViewHolder extends RecyclerView.ViewHolder {

    ItemApodRecyclerBinding binding;

    public ApodRecyclerViewHolder(ItemApodRecyclerBinding binding, ApodRecyclerOnItemClickInterface recyclerOnItemClickInterface) {
        super(binding.getRoot());
        this.binding = binding;
        itemView.setOnClickListener(view -> {
            if(recyclerOnItemClickInterface != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    recyclerOnItemClickInterface.onApodRecyclerItemClick(position);
                }
            }
        });
    }
}
