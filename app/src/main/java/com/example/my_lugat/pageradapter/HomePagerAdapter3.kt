package com.example.my_lugat.pageradapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_lugat.fragment.TechnologyFragment
import com.example.my_lugat.fragment.WordsFragment

class HomePagerAdapter3(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            TechnologyFragment()
        } else {
            WordsFragment()
        }
    }
}