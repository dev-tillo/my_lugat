package com.example.my_lugat.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databinding.SettingRvcBinding

class SettingAdapter(var listener: onItemClicked) :
    ListAdapter<Category, SettingAdapter.Vh>(MyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(SettingRvcBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.settingRvcBinding.apply {
            name.text = item.name

            pop.setOnClickListener {
                listener.onCliked(item, position, holder.settingRvcBinding.pop)
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(var settingRvcBinding: SettingRvcBinding) :
        RecyclerView.ViewHolder(settingRvcBinding.root) {
    }

    interface onItemClicked {
        fun onCliked(category: Category, position: Int, imageView: ImageView)
    }
}