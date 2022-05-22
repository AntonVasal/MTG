package com.example.mtg.apodKotlin.old

import androidx.recyclerview.widget.DiffUtil
import com.example.mtg.apodKotlin.ApodsModel

class ApodsRecyclerDiffCallback : DiffUtil.ItemCallback<ApodsModel>() {
    override fun areItemsTheSame(oldItem: ApodsModel, newItem: ApodsModel): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: ApodsModel, newItem: ApodsModel): Boolean {
        return oldItem == newItem
    }
}