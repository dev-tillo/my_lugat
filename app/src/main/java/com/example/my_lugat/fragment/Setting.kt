package com.example.my_lugat.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.my_lugat.R
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentSettingBinding
import com.example.my_lugat.databinding.ItemAddBinding
import com.example.my_lugat.pageradapter.HomePagerAdapter2
import com.example.my_lugat.pageradapter.HomePagerAdapter3
import nl.joery.animatedbottombar.AnimatedBottomBar

class Setting : Fragment() {

    lateinit var fraging: FragmentSettingBinding
    lateinit var data: RoomData
    lateinit var homePagerAdapter2: HomePagerAdapter3

    private var param1: Dictionary? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable("key") as Dictionary?
            param2 = it.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fraging = FragmentSettingBinding.inflate(inflater, container, false)
        data = RoomData.getInstance(requireContext())
        fraging.apply {
            homePagerAdapter2 = HomePagerAdapter3(this@Setting)
            view.adapter = homePagerAdapter2
            view.isUserInputEnabled = false

            view.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 0) {
                        navig.selectedItemId = R.id.kategory
                    } else {
                        navig.selectedItemId = R.id.dictionery
                    }
                }
            })

            left.setOnClickListener {
                findNavController().popBackStack()
            }

            fraging.navig.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.kategory -> {
                        plus.setOnClickListener {
                            val builder = AlertDialog.Builder(requireContext())
                            val customDialogBinding = ItemAddBinding.inflate(layoutInflater)
                            builder.setView(customDialogBinding.root)
                            val create = builder.create()
                            customDialogBinding.yoq.setOnClickListener {
                                create.dismiss()
                            }
                            customDialogBinding.xa.setOnClickListener {
                                val category_name =
                                    customDialogBinding.name.text.toString().trim()
                                if (category_name != "") {
                                    val category = Category(name = category_name)
                                    data.dicDao().addCategory(category)
                                    create.dismiss()
                                    Toast.makeText(requireContext(),
                                        "Kategoriya saqlandi",
                                        Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            create.show()
                        }
                    }
                    R.id.dictionery -> {
                        plus.setOnClickListener {
                            findNavController().navigate(R.id.add2)
                        }
                    }
                }
                true
            }
            return fraging.root
        }
    }
}
//            navig.setOnTabInterceptListener(object : AnimatedBottomBar.OnTabInterceptListener {
//                override fun onTabIntercepted(
//                    lastIndex: Int,
//                    lastTab: AnimatedBottomBar.Tab?,
//                    newIndex: Int,
//                    newTab: AnimatedBottomBar.Tab,
//                ): Boolean {
//                    if (newTab.id == R.id.kategory && newIndex == 0) {
//                        plus.setOnClickListener {
//                            val builder = AlertDialog.Builder(requireContext())
//                            val customDialogBinding = ItemAddBinding.inflate(layoutInflater)
//                            builder.setView(customDialogBinding.root)
//                            val create = builder.create()
//                            customDialogBinding.yoq.setOnClickListener {
//                                create.dismiss()
//                            }
//                            customDialogBinding.xa.setOnClickListener {
//                                val category_name =
//                                    customDialogBinding.name.text.toString().trim()
//                                if (category_name != "") {
//                                    val category = Category(name = category_name)
//                                    data.dicDao().addCategory(category)
//                                    create.dismiss()
//                                    Toast.makeText(requireContext(),
//                                        "Kategoriya saqlandi",
//                                        Toast.LENGTH_SHORT)
//                                        .show()
//                                }
//                            }
//                            create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                            create.show()
//                        }
//                        return true
//                    } else {
//                        lastTab?.id == R.id.dictionery && newIndex != 0
//                        plus.setOnClickListener {
//                            findNavController().navigate(R.id.add2)
//                        }
//                    }
//                    return true
//                }
//            })
//AnimatedBottomBar.Badge(
//                text = "99",
//                backgroundColor = Color.RED,
//                textColor = Color.GREEN,
//                textSize = 12
//            )