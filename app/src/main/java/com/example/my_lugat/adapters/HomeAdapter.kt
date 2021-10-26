package com.example.my_lugat.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databinding.HomeRvcBinding

class HomeAdapter(var listener: onItemClicked) :
    ListAdapter<Dictionary, HomeAdapter.Vh>(MyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HomeRvcBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.homeRvcBinding.apply {
            name.text = item.name
            sinonim.text = item.sinonim

            image.setOnClickListener {
                listener.onCliked(item, position)
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Dictionary>() {
        override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(var homeRvcBinding: HomeRvcBinding) :
        RecyclerView.ViewHolder(homeRvcBinding.root) {
    }

    interface onItemClicked {
        fun onCliked(dictionary: Dictionary, position: Int)
    }
}