package com.example.my_lugat.fragment

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.room.RoomDatabase
import com.example.my_lugat.R
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentInfoBinding

class Info : Fragment() {

    lateinit var fraging: FragmentInfoBinding

    private var dictionary: Dictionary? = null
    private var param2: String? = null
    lateinit var roomData: RoomData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dictionary = it.getSerializable("key") as Dictionary?
            param2 = it.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fraging = FragmentInfoBinding.inflate(inflater, container, false)
        roomData = RoomData.getInstance(requireContext())
        fraging.apply {
            left.setOnClickListener {
                findNavController().popBackStack()
            }

            image.setImageURI(Uri.parse(dictionary?.image.toString()))
            name.text = dictionary?.name
            name2.text = dictionary?.name
            sinonim.text = dictionary?.sinonim

//            blur.setOnClickListener {
//                if (dictionary?.isliked == 0) {
//                    yurak.setImageResource(R.drawable.ic_heart)
//                    roomData.dicDao().editLugat()
//                } else {
//                    yurak.setImageResource(R.drawable.ic_liked)
//                    roomData.dicDao().editLugat()
//                }
        }
        return fraging.root
    }
}