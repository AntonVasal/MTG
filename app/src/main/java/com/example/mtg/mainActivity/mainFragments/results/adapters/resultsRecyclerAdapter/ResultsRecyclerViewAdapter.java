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


    public ResultsRecyclerViewAdapter(ArrayList<AddResultsModel> addItemList, ArrayList<MultiResultsModel> multiItemList, ArrayList<SubResultsModel> subItemList, ArrayList<DivResultsModel> divItemList, Context mContext, int typeTask, int typeNumber) {
        this.addItemList = addItemList;
        this.multiItemList = multiItemList;
        this.subItemList = subItemList;
        this.divItemList = divItemList;
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
        Glide.with(mContext)
                .load(addItemList.get(position).getImageUrl())
                .into(holder.userImg);
        holder.userName.setText(addItemList.get(position).getNickname());
        switch (typeTask) {
            case 1:
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
        return addItemList.size();
    }
}
