package com.example.mtg.ui.activities.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mtg.R;
import com.example.mtg.models.countModels.AddResultsModel;
import com.example.mtg.models.countModels.DivResultsModel;
import com.example.mtg.models.countModels.MultiResultsModel;
import com.example.mtg.models.countModels.SubResultsModel;

import java.util.ArrayList;

public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<ResultsRecyclerViewHolder> {
    private final OnItemResultsRecyclerClickInterface onItemResultsRecyclerClickInterface;
    private ArrayList<AddResultsModel> addItemList;
    private ArrayList<MultiResultsModel> multiItemList;
    private ArrayList<SubResultsModel> subItemList;
    private ArrayList<DivResultsModel> divItemList;
    private final Context mContext;
    private final int typeTask;
    private int typeNumber;

    public void setAddItemList(ArrayList<AddResultsModel> addItemList, int typeNumber) {
        this.addItemList = addItemList;
        this.typeNumber = typeNumber;
    }

    public void setMultiItemList(ArrayList<MultiResultsModel> multiItemList, int typeNumber) {
        this.multiItemList = multiItemList;
        this.typeNumber = typeNumber;
    }

    public void setSubItemList(ArrayList<SubResultsModel> subItemList, int typeNumber) {
        this.subItemList = subItemList;
        this.typeNumber = typeNumber;
    }

    public void setDivItemList(ArrayList<DivResultsModel> divItemList, int typeNumber) {
        this.divItemList = divItemList;
        this.typeNumber = typeNumber;
    }

    public ResultsRecyclerViewAdapter(Context mContext, OnItemResultsRecyclerClickInterface onItemResultsRecyclerClickInterface,int typeTask) {
        this.mContext = mContext;
        this.typeTask = typeTask;
        this.onItemResultsRecyclerClickInterface = onItemResultsRecyclerClickInterface;
    }

    @NonNull
    @Override
    public ResultsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_for_results, parent, false);
        return new ResultsRecyclerViewHolder(itemView, onItemResultsRecyclerClickInterface, typeNumber);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsRecyclerViewHolder holder, int position) {
        holder.userName.setSelected(true);
        switch (typeTask) {
            case 1:
                Glide.with(mContext)
                        .load(addItemList.get(position).getImageUrl())
                        .placeholder(R.drawable.ic_user_main)
                        .error(R.drawable.ic_user_main)
                        .apply(new RequestOptions().override(50, 50))
                        .into(holder.userImg);
                holder.userName.setText(addItemList.get(position).getNickname());
                switch (typeNumber) {
                    case 1:
                        holder.userScore.setText(String.valueOf(addItemList.get(position).getAddNaturalScore()));
                        break;
                    case 2:
                        holder.userScore.setText(String.valueOf(addItemList.get(position).getAddIntegerScore()));
                        break;
                    case 3:
                        holder.userScore.setText(String.valueOf(addItemList.get(position).getAddDecimalScore()));
                        break;
                }
                break;
            case 2:
                Glide.with(mContext)
                        .load(multiItemList.get(position).getImageUrl())
                        .placeholder(R.drawable.ic_user_main)
                        .error(R.drawable.ic_user_main)
                        .apply(new RequestOptions().override(50, 50))
                        .into(holder.userImg);
                holder.userName.setText(multiItemList.get(position).getNickname());
                switch (typeNumber) {
                    case 1:
                        holder.userScore.setText(String.valueOf(multiItemList.get(position).getMultiNaturalScore()));
                        break;
                    case 2:
                        holder.userScore.setText(String.valueOf(multiItemList.get(position).getMultiIntegerScore()));
                        break;
                    case 3:
                        holder.userScore.setText(String.valueOf(multiItemList.get(position).getMultiDecimalScore()));
                        break;
                }
                break;
            case 3:
                Glide.with(mContext)
                        .load(subItemList.get(position).getImageUrl())
                        .placeholder(R.drawable.ic_user_main)
                        .error(R.drawable.ic_user_main)
                        .apply(new RequestOptions().override(50, 50))
                        .into(holder.userImg);
                holder.userName.setText(subItemList.get(position).getNickname());
                switch (typeNumber) {
                    case 1:
                        holder.userScore.setText(String.valueOf(subItemList.get(position).getSubNaturalScore()));
                        break;
                    case 2:
                        holder.userScore.setText(String.valueOf(subItemList.get(position).getSubIntegerScore()));
                        break;
                    case 3:
                        holder.userScore.setText(String.valueOf(subItemList.get(position).getSubDecimalScore()));
                        break;
                }
                break;
            case 4:
                Glide.with(mContext)
                        .load(divItemList.get(position).getImageUrl())
                        .placeholder(R.drawable.ic_user_main)
                        .error(R.drawable.ic_user_main)
                        .apply(new RequestOptions().override(50, 50))
                        .into(holder.userImg);
                holder.userName.setText(divItemList.get(position).getNickname());
                switch (typeNumber) {
                    case 1:
                        holder.userScore.setText(String.valueOf(divItemList.get(position).getDivNaturalScore()));
                        break;
                    case 2:
                        holder.userScore.setText(String.valueOf(divItemList.get(position).getDivIntegerScore()));
                        break;
                    case 3:
                        holder.userScore.setText(String.valueOf(divItemList.get(position).getDivDecimalScore()));
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (typeTask) {
            case 1:
                return addItemList.size();
            case 2:
                return multiItemList.size();
            case 3:
                return subItemList.size();
            case 4:
                return divItemList.size();
            default:
                return 0;
        }
    }
}
