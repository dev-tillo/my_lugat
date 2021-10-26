package com.example.my_lugat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentBlankBinding
import com.example.my_lugat.pageradapter.HomePagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BlankFragment : Fragment() {

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    lateinit var fraging: FragmentBlankBinding
    lateinit var adapter: HomePagerAdapter
    lateinit var list: ArrayList<Dictionary>
    lateinit var roomData: RoomData

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fraging = FragmentBlankBinding.inflate(inflater, container, false)

        roomData = RoomData.getInstance(requireContext())
        adapter = HomePagerAdapter(this)

        fraging.viewpager1.adapter = adapter
        fraging.viewpager1.isUserInputEnabled = false
        fraging.apply {

            qalam.setOnClickListener {
                findNavController().navigate(R.id.setting)
            }

            fraging.viewpager1.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 0) {
                        fraging.navigation.selectedItemId = R.id.asosiy
                    } else {
                        fraging.navigation.selectedItemId = R.id.heart
                    }
                }
            })

            fraging.navigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.asosiy -> fraging.viewpager1.currentItem = 0
                    R.id.heart -> fraging.viewpager1.currentItem = 1
                }
                true
            }
        }
        return fraging.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}