package com.example.my_lugat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    var ispressed = true
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        handler = Handler(Looper.getMainLooper())
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_support_nav).navigateUp()
    }

    override fun onBackPressed() {
        if (ispressed) {
            super.onBackPressed()
            return
        }
        ispressed = true
        handler.postDelayed({
            ispressed = false
        }, 2000)
        Toast.makeText(this, "Yana bosing", Toast.LENGTH_SHORT).show()
    }
}