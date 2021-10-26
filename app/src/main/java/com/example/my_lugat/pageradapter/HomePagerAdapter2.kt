package com.example.my_lugat.pageradapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_lugat.Asosiy
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.fragment.TechnologyFragment
import com.example.my_lugat.fragment.WordsFragment

class HomePagerAdapter2(
    fm: Fragment,
    var list: List<Category>,
) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return Asosiy.newInstance(list[position])
    }
}