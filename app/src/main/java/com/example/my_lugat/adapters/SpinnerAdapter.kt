package com.example.my_lugat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.my_lugat.classses.Category
import com.example.my_lugat.databinding.ItemSpinBinding

class SpinnerAdapter(var list: List<Category>) : BaseAdapter() {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Category = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemSpinBinding = if (convertView == null) {
            ItemSpinBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            ItemSpinBinding.bind(convertView)
        }
        binding.text.text = list[position].name
        return binding.root
    }
}