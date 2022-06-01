package com.example.mtg.apodKotlin.recyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.mtg.R
import com.example.mtg.apodKotlin.model.ApodsModel
import com.example.mtg.databinding.ItemApodRecyclerBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApodsRecyclerAdapter(private val context: Context)
    : RecyclerView.Adapter<ApodsRecyclerViewHolder>(){

    private val differ: AsyncListDiffer<ApodsModel> = AsyncListDiffer(this, ApodsRecyclerDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodsRecyclerViewHolder {
        val binding : ItemApodRecyclerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_apod_recycler, parent, false)
        return ApodsRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApodsRecyclerViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<ApodsModel>) {
        differ.submitList(list)
    }

    fun currentList(): List<ApodsModel> {
        return differ.currentList
    }
}




