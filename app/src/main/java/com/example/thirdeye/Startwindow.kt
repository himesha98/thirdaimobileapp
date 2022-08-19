package com.example.thirdeye


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RelativeLayout

class Startwindow : AppCompatActivity() {
    private lateinit var firstScreen : RelativeLayout
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startwindow)
        supportActionBar?.hide()
        firstScreen = findViewById(R.id.first_screen)

        firstScreen.setOnClickListener {
        }
        handler.postDelayed({
            launchThirdEye()
        }, 10000)



    }

    private fun launchThirdEye(){
        val intent = Intent(this, Categories::class.java)
        finish()
        startActivity(intent)
    }
}