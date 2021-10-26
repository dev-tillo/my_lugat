package com.example.my_lugat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var ispressed = true
    lateinit var handler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler(Looper.getMainLooper())


        val countDownTimer = object : CountDownTimer(2000, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        countDownTimer.start()
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