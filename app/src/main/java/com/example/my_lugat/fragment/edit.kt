package com.example.my_lugat.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.my_lugat.BuildConfig
import com.example.my_lugat.R
import com.example.my_lugat.adapters.SpinnerAdapter
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import com.example.my_lugat.databace.RoomData
import com.example.my_lugat.databinding.FragmentEditBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class edit : Fragment() {

    lateinit var fraging: FragmentEditBinding
    private var fileAbsolutePath: String = ""
    lateinit var currentPhotoPath: String
    private var image_path: Uri? = null
    var count1 = 0
    lateinit var roomdata: RoomData
    lateinit var categoryList: ArrayList<Category>
    lateinit var spinnerAdapter: SpinnerAdapter

    private var dictionary: Dictionary? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dictionary = it.getSerializable("key") as Dictionary?
            param2 = it.getString("")
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fraging = FragmentEditBinding.inflate(inflater, container, false)
        roomdata = RoomData.getInstance(requireContext())
        fraging.apply {
            adimage.setOnClickListener {
                addImage()
            }
            adimage.setImageURI(Uri.parse(dictionary?.image.toString()))
            dic.setText(dictionary?.name)
            dicsinonim.setText(dictionary?.sinonim)

            saqlash.setOnClickListener {
                val dictionary = Dictionary(name = dic.text.toString(),
                    sinonim = dicsinonim.text.toString(),
                    image = image_path.toString(),
                    isliked = 0,
                    categname = spinner.selectedItemId.toString()
                )
                roomdata.dicDao().editLugat(dictionary)
            }
            bekor.setOnClickListener {
                findNavController().popBackStack()
            }
            left.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return fraging.root
    }

    private fun addImage() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission")
            .setMessage("Rasmni cameradan olmoqchimisiz yoki galareyadan?")
            .setNegativeButton(
                "Galarry",
                DialogInterface.OnClickListener { dialogInterface, _ ->
                    Dexter.withContext(requireContext())
                        .withPermission(
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ).withListener(object : PermissionListener {
                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                                getImageContent.launch("image/*")
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                permission: PermissionRequest?,
                                token: PermissionToken?,
                            ) {
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Permission")
                                    .setMessage("Iltimos malumotlaringizdan foydalanish uchun ruhsat bering")
                                    .setNegativeButton(
                                        "Yoq",
                                        DialogInterface.OnClickListener { dialogInterface, _ ->
                                            dialogInterface.dismiss()
                                            token?.cancelPermissionRequest()
                                        })
                                    .setPositiveButton(
                                        "Xa",
                                        DialogInterface.OnClickListener { dialogInterface, _ ->
                                            dialogInterface.dismiss()
                                            token?.continuePermissionRequest()
                                        })
                                    .show()
                            }

                            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                                if (count1 > 0) {
                                    var intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    var uri: Uri =
                                        Uri.fromParts("package", requireContext().packageName, null)
                                    intent.data = uri
                                    startActivity(intent)
                                }
                                count1++
                            }
                        }).check()
                    dialogInterface.dismiss()
                }).setPositiveButton(
                "Camera",
                DialogInterface.OnClickListener { dialogInterface, _ ->
                    Dexter.withContext(requireContext()).withPermission(
                        Manifest.permission.CAMERA
                    ).withListener(object : PermissionListener {
                        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            onResult()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token1: PermissionToken?,
                        ) {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Permission")
                                .setMessage("Iltimos malumotlaringizdan foydalanish uchun ruhsat bering")
                                .setNegativeButton(
                                    "Yoq",
                                    DialogInterface.OnClickListener { dialogInterface, _ ->
                                        dialogInterface.dismiss()
                                        token1?.cancelPermissionRequest()
                                    })
                                .setPositiveButton(
                                    "Xa",
                                    DialogInterface.OnClickListener { dialogInterface, _ ->
                                        dialogInterface.dismiss()
                                        token1?.continuePermissionRequest()
                                    })
                                .show()
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            if (count1 > 0) {
                                var intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                var uri: Uri =
                                    Uri.fromParts("package", requireContext().packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                            count1++
                        }
                    }).check()
                    dialogInterface.dismiss()
                })
            .show()
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) {
                return@registerForActivityResult
            }
            image_path = uri
            fraging.adimage.setImageURI(uri)
            val openInputStream = activity?.contentResolver?.openInputStream(uri)
            val currentTimeMillis = System.currentTimeMillis()
            val file = File(activity?.filesDir, "$currentTimeMillis.jpg")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()
            fileOutputStream.close()
            fileAbsolutePath = file.absolutePath
        }

    private fun onResult() {
        val photoFile = try {
            createImageFile()
        } catch (e: Exception) {
            null
        }
        photoFile?.also {
            val uri = FileProvider.getUriForFile(
                requireContext(), BuildConfig.APPLICATION_ID,
                it
            )
            getCameraImage.launch(uri)
        }
    }

    private val getCameraImage =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                fraging.adimage.setImageURI(Uri.fromFile(File(currentPhotoPath)))
                image_path = Uri.fromFile(File(currentPhotoPath))
            }
        }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val m = System.currentTimeMillis()
        val externalFilesDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("my_images_$m", ".jpg", externalFilesDir)
            .apply {
                currentPhotoPath = absolutePath
            }
    }
}