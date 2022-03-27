package com.bignerdranch.android.forage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.forage.Data.Forage
import com.bignerdranch.android.forage.databinding.ItemViewBinding

class ForageListAdapter(private val onItemClicked: (Forage) -> Unit) :
    ListAdapter<Forage, ForageListAdapter.ForageViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForageViewHolder {
        val view = ItemViewBinding.inflate(LayoutInflater.from(parent.context))
        return ForageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForageViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }


    class ForageViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forage: Forage) {
            binding.apply {
                tvName.text = forage.name
                tvLocation.text = forage.location
            }

        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Forage>() {
            override fun areItemsTheSame(oldItem: Forage, newItem: Forage): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Forage, newItem: Forage): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}

