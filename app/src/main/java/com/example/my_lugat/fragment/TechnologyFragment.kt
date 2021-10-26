package com.example.my_lugat.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.my_lugat.R
import com.example.my_lugat.adapters.HomeAdapter
import com.example.my_lugat.adapters.SettingAdapter
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentTechnologyBinding
import com.example.my_lugat.databinding.ItemAddBinding
import com.example.my_lugat.databinding.ItemDeletBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TechnologyFragment : Fragment() {

    lateinit var fraging: FragmentTechnologyBinding
    lateinit var settingAdapter: SettingAdapter
    lateinit var roomData: RoomData
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
        fraging = FragmentTechnologyBinding.inflate(inflater, container, false)
        roomData = RoomData.getInstance(requireContext())
        fraging.apply {
            settingAdapter = SettingAdapter(object : SettingAdapter.onItemClicked {
                override fun onCliked(category: Category, position: Int, imageView: ImageView) {
                    val poppup = PopupMenu(requireContext(), imageView)
                    poppup.menuInflater.inflate(R.menu.popmenu, poppup.menu)
                    poppup.setOnMenuItemClickListener { menuitem ->
                        val id = menuitem.itemId
                        if (id == R.id.edit) {
                            val builder = AlertDialog.Builder(requireContext())
                            val customDialogBinding = ItemAddBinding.inflate(layoutInflater)
                            builder.setView(customDialogBinding.root)
                            val create = builder.create()

                            customDialogBinding.name.setText(category.name)

                            customDialogBinding.yoq.setOnClickListener {
                                create.dismiss()
                            }

                            customDialogBinding.xa.setOnClickListener {
                                val category_name = customDialogBinding.name.text.toString().trim()
                                if (category_name != "") {
                                    val category = Category(name = category_name.toString())
                                    roomData.dicDao().editCategory(category)
                                    Toast.makeText(requireContext(),
                                        "Kategoriya saqlandi",
                                        Toast.LENGTH_SHORT)
                                        .show()
                                }
                                create.dismiss()
                            }
                            create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            create.show()
                        } else if (id == R.id.delet) {
                            val builder = AlertDialog.Builder(requireContext())
                            val customDialogBinding = ItemDeletBinding.inflate(layoutInflater)
                            builder.setView(customDialogBinding.root)
                            val create = builder.create()
                            customDialogBinding.yoq.setOnClickListener {
                                create.dismiss()
                            }
                            customDialogBinding.xa.setOnClickListener {
                                roomData.dicDao().deleteCategory(category)
                                create.dismiss()
                            }
                            create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            create.show()
                        }
                        false
                    }
                    poppup.show()
                }
            })
            rvc.adapter = settingAdapter

            roomData.dicDao()
                .getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    settingAdapter.submitList(it)
                }
        }
        return fraging.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TechnologyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}