package com.example.my_lugat.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databinding.HomeRvcBinding
import com.example.my_lugat.databinding.SettingRvcwordBinding

class SettingAdapter2(var listener: onItemClicked) :
    ListAdapter<Dictionary, SettingAdapter2.Vh>(MyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(SettingRvcwordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.settingRvcwordBinding.apply {
            name.text = item.name
            sinonim.text = item.sinonim

            pop.setOnClickListener {
                listener.onCliked(item, position, holder.settingRvcwordBinding.pop)
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

    inner class Vh(var settingRvcwordBinding: SettingRvcwordBinding) :
        RecyclerView.ViewHolder(settingRvcwordBinding.root) {
    }

    interface onItemClicked {
        fun onCliked(dictionary: Dictionary, position: Int, imageView: ImageView)
    }
}