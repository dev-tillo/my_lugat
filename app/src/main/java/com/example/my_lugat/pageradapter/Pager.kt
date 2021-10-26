package com.example.my_lugat.pageradapter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.RoomDatabase
import com.example.my_lugat.classses.Category
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databace.RoomData.Companion.getInstance
import com.example.my_lugat.databinding.FragmentPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Pager : Fragment() {

    lateinit var fragming: FragmentPagerBinding
    lateinit var homePagerAdapter2: HomePagerAdapter2
    lateinit var appDatabase: RoomData
    lateinit var list: ArrayList<Category>

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragming = FragmentPagerBinding.inflate(inflater, container, false)
        appDatabase = getInstance(requireContext())

        list = ArrayList(appDatabase.dicDao().getAllCategoryList())

        homePagerAdapter2 = HomePagerAdapter2(this, list)
        fragming.viewpager2.adapter = homePagerAdapter2

        fragming.apply {
            TabLayoutMediator(fragming.tabLayout, fragming.viewpager2) { tab, position ->
                tab.text = list[position].name
            }.attach()
        }
        return fragming.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Pager().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}