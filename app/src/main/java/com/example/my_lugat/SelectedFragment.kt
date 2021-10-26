package com.example.my_lugat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.my_lugat.adapters.HomeAdapter
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentSelectedBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList


class SelectedFragment() : Fragment() {

    private var param1: String? = null
    private val ARG_PARAM1: String = ""

    lateinit var fraging: FragmentSelectedBinding
    lateinit var homeAdapter: HomeAdapter
    lateinit var roomData: RoomData
    lateinit var list: ArrayList<Dictionary>

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fraging = FragmentSelectedBinding.inflate(inflater, container, false)
        roomData = RoomData.getInstance(requireContext())
        fraging.apply {

            homeAdapter = HomeAdapter(object : HomeAdapter.onItemClicked {
                override fun onCliked(dictionary: Dictionary, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable("key", dictionary)
                    findNavController().navigate(R.id.info2, bundle)
                }
            })
            rvc.adapter = homeAdapter

            roomData.dicDao()
                .getAllLugat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    list = ArrayList(it.filter {
                        it.isliked == it.id
                    })
                    homeAdapter.submitList(list)
                }
        }
        return fraging.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SelectedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}