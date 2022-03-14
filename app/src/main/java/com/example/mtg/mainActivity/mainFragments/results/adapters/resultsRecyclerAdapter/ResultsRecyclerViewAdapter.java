package com.example.mtg.mainActivity.mainFragments.results.adapters.resultsRecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtg.R;
import com.example.mtg.mainActivity.count.countModels.AddResultsModel;
import com.example.mtg.mainActivity.count.countModels.DivResultsModel;
import com.example.mtg.mainActivity.count.countModels.MultiResultsModel;
import com.example.mtg.mainActivity.count.countModels.SubResultsModel;

import java.util.ArrayList;

public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<ResultsRecyclerViewHolder> {
    ArrayList<AddResultsModel> addItemList;
    ArrayList<MultiResultsModel> multiItemList;
    ArrayList<SubResultsModel> subItemList;
    ArrayList<DivResultsModel> divItemList;
    Context mContext;
    int typeTask;
    int typeNumber;

    public void setAddItemList(ArrayList<AddResultsModel> addItemList) {
        this.addItemList = addItemList;
    }

    public void setMultiItemList(ArrayList<MultiResultsModel> multiItemList) {
        this.multiItemList = multiItemList;
    }

    public void setSubItemList(ArrayList<SubResultsModel> subItemList) {
        this.subItemList = subItemList;
    }

    public void setDivItemList(ArrayList<DivResultsModel> divItemList) {
        this.divItemList = divItemList;
    }

    public ResultsRecyclerViewAdapter (Context mContext, int typeTask, int typeNumber) {
        this.mContext = mContext;
        this.typeTask = typeTask;
        this.typeNumber = typeNumber;
    }



    @NonNull
    @Override
    public ResultsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_for_results, parent, false);
        return new ResultsRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsRecyclerViewHolder holder, int position) {
        switch (typeTask) {
            case 1:
                Glide.with(mContext)
                        .load(addItemList.get(position).getImageUrl())
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
                        .into(holder.userImg);
                holder.userName.setText(multiItemList.get(position).getNickname());
                switch (typeNumber){
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
                        .into(holder.userImg);
                holder.userName.setText(subItemList.get(position).getNickname());
                switch (typeNumber){
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
                        .into(holder.userImg);
                holder.userName.setText(divItemList.get(position).getNickname());
                switch (typeNumber){
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
        switch (typeTask){
            case 1:
                return addItemList.size();
            case 2:
                return multiItemList.size();
            case 3:
                return subItemList.size();
            case 4:
                return divItemList.size();
            default: return 0;
        }
    }
}
