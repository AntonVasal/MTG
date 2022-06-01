package com.example.mtg.apodKotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mtg.apodKotlin.recyclerViewAdapter.ApodsRecyclerAdapter

abstract class BaseRecyclerViewAdapter<Binding : ViewDataBinding, DATA>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<Binding>>() {

    protected lateinit var binding: Binding
    abstract var arrayList: ArrayList<DATA>
    abstract val context: Context
    abstract val layoutId: Int

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Binding> {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false)
        return BaseViewHolder(binding)
   }

    fun <T> autoNotify(oldList: ArrayList<T>, newList: ArrayList<T>, compare: (T, T) -> Boolean) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compare(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }


    override fun getItemCount(): Int = arrayList.size


    class BaseViewHolder<Binding : ViewDataBinding>(val binding: Binding) : RecyclerView.ViewHolder(binding.root)

}



