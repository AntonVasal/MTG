package com.example.mtg.apodKotlin.recyclerViewAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mtg.apodKotlin.model.ApodsModel

class ApodsRecyclerDiffCallback : DiffUtil.ItemCallback<ApodsModel>() {
    override fun areItemsTheSame(oldItem: ApodsModel, newItem: ApodsModel): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: ApodsModel, newItem: ApodsModel): Boolean {
        return oldItem == newItem
    }
}