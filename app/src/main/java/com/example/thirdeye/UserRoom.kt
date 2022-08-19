package com.example.thirdeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class UserRoom : AppCompatActivity() {

    private lateinit var userLessons : LinearLayout
    private lateinit var userMusic : LinearLayout
    private lateinit var userPodcasts :LinearLayout
    private lateinit var userLibrary:LinearLayout
    private lateinit var userSeminars :LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_room)
        supportActionBar?.hide()


        userLessons = findViewById(R.id.ur_lessons_wrapper)
        userMusic = findViewById(R.id.ur_music_wrapper)
        userPodcasts = findViewById(R.id.ur_podcasts_wrapper)
        userLibrary = findViewById(R.id.ur_library_wrapper)
        userSeminars = findViewById(R.id.ur_seminars_wrapper)


        userLessons.setOnClickListener{
            val intent = Intent(this@UserRoom, UserRoomPlayContents::class.java)
            intent.putExtra("category","Lessons")
            startActivity(intent)
        }
        userMusic.setOnClickListener{
            val intent = Intent(this@UserRoom, UserRoomPlayContents::class.java)
            intent.putExtra("category","Music")
            startActivity(intent)
        }
        userPodcasts.setOnClickListener{
            val intent = Intent(this@UserRoom, UserRoomPlayContents::class.java)
            intent.putExtra("category","Podcasts")
            startActivity(intent)
        }
        userLibrary.setOnClickListener{
            val intent = Intent(this@UserRoom, UserRoomPlayContents::class.java)
            intent.putExtra("category","Library")
            startActivity(intent)
        }
        userSeminars.setOnClickListener{
            val intent = Intent(this@UserRoom, UserRoomPlayContents::class.java)
            intent.putExtra("category","Seminars")
            startActivity(intent)
        }


    }
}