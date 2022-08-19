package com.example.thirdeye

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule

class Categories : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var education: RelativeLayout
    private lateinit var entertainment: RelativeLayout
    private lateinit var library: RelativeLayout
    private lateinit var searchWrapper: RelativeLayout
    private lateinit var speechText: TextView
    private lateinit var microphone: Button
    private lateinit var userButton: ImageButton
    private val RQ_SPEECH_REC = 10000
    private var speechResult: String = ""
    lateinit var mediaPlayer: MediaPlayer
    lateinit var textToSpeech: TextToSpeech
    private lateinit var searchbutton: ImageView
    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        supportActionBar?.hide()
        var mediaPlayer = MediaPlayer()
        textToSpeech = TextToSpeech(this@Categories, this@Categories)
        handler.postDelayed({
            speakOUt()
        }, 10000)











        userButton = findViewById(R.id.user_room)
        education = findViewById(R.id.education)
        entertainment = findViewById(R.id.entertainment)
        library = findViewById(R.id.library)
        searchWrapper = findViewById(R.id.search_wrapper)
        microphone = findViewById(R.id.microphone)

        education.setOnClickListener {
            gradeList()
        }

        entertainment.setOnClickListener {
            catList()
        }

        library.setOnClickListener {
            library()
        }
        microphone.setOnClickListener {
            askspeechinput()
        }
        userButton.setOnClickListener {
            userroom()
        }
        Thread.sleep(5000)



    }

    private fun speakOUt() {
        textToSpeech.speak("Welcome to Third AI, Choose your category....   Education, Entertainment, Library", TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechResult = result?.get(0).toString()
            if (speechResult == "education" || speechResult == "adyaapana") {
                gradeList()
            } else if (speechResult == "entertainment") {
                catList()
            } else if (speechResult == "library") {
                library()
            }else if (speechResult == "user room" || speechResult == "user"|| speechResult == "room") {
                userroom()
            }
            Log.d(ContentValues.TAG, "speech input: " + result?.get(0).toString())
        }


    }

    private fun gradeList() {
        val intent = Intent(this@Categories, Choose_grade::class.java)
        startActivity(intent)
    }

    private fun catList() {
        val intent = Intent(this@Categories, Entertainment::class.java)
        startActivity(intent)
    }

    private fun library() {
        val intent = Intent(this@Categories, Library::class.java)
        startActivity(intent)
    }

    private fun userroom() {
        val intent = Intent(this@Categories, UserRoom::class.java)
        startActivity(intent)
    }


    private fun askspeechinput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "speech recog not available", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }



    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

        } else {
            Toast.makeText(applicationContext, "Failed text to speech", Toast.LENGTH_SHORT).show()
        }
    }

}