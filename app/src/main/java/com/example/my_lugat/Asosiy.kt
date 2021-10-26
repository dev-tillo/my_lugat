package com.example.my_lugat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.my_lugat.adapters.HomeAdapter
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentAsosiyBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Asosiy : Fragment() {

    private val ARG_PARAM1 = "category"
    private val ARG_PARAM2 = "param2"

    lateinit var list: ArrayList<Dictionary>
    lateinit var roomData: RoomData
    lateinit var fraging: FragmentAsosiyBinding
    lateinit var homeAdapter: HomeAdapter

    private var param1: Category? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Category?
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fraging = FragmentAsosiyBinding.inflate(inflater, container, false)
        roomData = RoomData.getInstance(requireContext())
        fraging.apply {

            homeAdapter = HomeAdapter(object : HomeAdapter.onItemClicked {
                override fun onCliked(dictionary: Dictionary, position: Int) {
                    val bundle = Bundle()
                    bundle.putSerializable("key", dictionary)
                    findNavController().navigate(R.id.info2, bundle)
                }
            })

            fraging.rvc.adapter = homeAdapter

            roomData.dicDao()
                .getAllLugat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    homeAdapter.submitList(it)
                }

        }
        return fraging.root
    }

    companion object {
        @JvmStatic
        fun newInstance(category: Category) =
            Asosiy().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, category)
                }
            }
    }
}