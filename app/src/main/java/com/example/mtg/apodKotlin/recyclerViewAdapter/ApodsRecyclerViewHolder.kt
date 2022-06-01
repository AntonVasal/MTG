package com.example.mtg.apodKotlin.recyclerViewAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mtg.apodKotlin.model.ApodsModel
import com.example.mtg.databinding.ItemApodRecyclerBinding

class ApodsRecyclerViewHolder(private val binding: ItemApodRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(apodsModel: ApodsModel){
//        binding.apodModel = apodsModel
    }

}
