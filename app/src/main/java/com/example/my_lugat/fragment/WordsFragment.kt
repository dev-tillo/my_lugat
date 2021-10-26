package com.example.my_lugat.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.my_lugat.R
import com.example.my_lugat.adapters.SettingAdapter2
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentWordsBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WordsFragment : Fragment() {

    lateinit var fraging: FragmentWordsBinding
    lateinit var settingAdapter2: SettingAdapter2
    lateinit var roomData: RoomData
    lateinit var list: ArrayList<Dictionary>

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
        fraging = FragmentWordsBinding.inflate(inflater, container, false)
        roomData = RoomData.getInstance(requireContext())
        fraging.apply {
            settingAdapter2 =
                SettingAdapter2(object : SettingAdapter2.onItemClicked {
                    override fun onCliked(
                        dictionary: Dictionary,
                        position: Int,
                        imageView: ImageView,
                    ) {
                        val poppup = PopupMenu(requireContext(), imageView)
                        poppup.menuInflater.inflate(R.menu.popmenu, poppup.menu)
                        poppup.setOnMenuItemClickListener { menuitem ->
                            val id = menuitem.itemId
                            if (id == R.id.edit) {
                                var bundle = Bundle()
                                bundle.putSerializable("key", dictionary)
                                findNavController().navigate(R.id.edit2, bundle)
                            } else if (id == R.id.delet) {
                                roomData.dicDao().deletLugat(dictionary)
                            }
                            false
                        }
                        poppup.show()
                    }
                })
            rvc.adapter = settingAdapter2

            roomData.dicDao()
                .getAllLugat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    settingAdapter2.submitList(it)
                }
        }
        return fraging.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WordsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}