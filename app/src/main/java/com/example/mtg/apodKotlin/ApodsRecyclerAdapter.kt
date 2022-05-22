package com.example.mtg.apodKotlin

import android.content.Context
import com.example.mtg.databinding.ItemApodRecyclerBinding

class ApodsRecyclerAdapter(override val context: Context, override val layoutId: Int, var arrayList: ArrayList<ApodsModel>)
    : BaseRecyclerViewAdapter<ItemApodRecyclerBinding>() {


    override fun onBindViewHolder(holder: BaseViewHolder<ItemApodRecyclerBinding>, position: Int) {
//       holder.binding.apodModel = arrayList[position]
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}