package com.example.my_lugat.pageradapter


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_lugat.Asosiy
import com.example.my_lugat.SelectedFragment
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary

class HomePagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            Pager()
        } else {
            SelectedFragment()
        }
    }
}