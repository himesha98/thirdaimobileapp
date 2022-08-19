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
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.fixedRateTimer

class Library : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var microphone: Button
    private val RQ_SPEECH_REC = 10000
    private var speechResult: String = ""
    lateinit var mediaPlayer: MediaPlayer
    lateinit var textToSpeech: TextToSpeech
    private lateinit var searchbutton: ImageView
    val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        supportActionBar?.hide()
        var mediaPlayer = MediaPlayer()
        textToSpeech = TextToSpeech(this@Library, this@Library)
        handler.postDelayed({
            speakOUt()
        }, 10000)
        microphone = findViewById(R.id.microphone)
        microphone.setOnClickListener {
            askspeechinput()
        }
    }

    private fun speakOUt() {
        textToSpeech.speak(
            "This is your entertainment section choose which one from music, podcasts, news or movies",
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

        } else {
            Toast.makeText(applicationContext, "Failed text to speech", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechResult = result?.get(0).toString()
            if (speechResult == "novels") {
                val intent = Intent(this@Library, PlayNovels::class.java)
                startActivity(intent)
            } else if (speechResult == "fictions") {
                val intent = Intent(this@Library, PlayNovels::class.java)
                startActivity(intent)
            } else if (speechResult == "seminars") {
                val intent = Intent(this@Library, PlaySeminars::class.java)
                startActivity(intent)
            }else if(speechResult == "back" || speechResult == "home" ){
                val intent = Intent(this@Library, Categories::class.java)
                startActivity(intent)
            }else if(speechResult == "user room" || speechResult == "user"|| speechResult == "room"){
                val intent = Intent(this@Library, UserRoom::class.java)
                startActivity(intent)
            }
            Log.d(ContentValues.TAG, "speech input: " + result?.get(0).toString())
        }
        /**searchWrapper.setOnDragListener { searchWrapper, dragEvent -> triggerUserRoom }
        searchWrapper.setOnClickListener{
        userroom()
        }
        if (triggerUserRoom) {
        userroom()
        }else{

        }*/

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

    private fun speechinput() {

        val fixedRateTimer = fixedRateTimer(
            name = "hello-timer",
            initialDelay = 100, period = 100
        ) {
            println("hello world!")
        }
        try {
            Thread.sleep(1000)
        } finally {
            fixedRateTimer.cancel();
        }
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.your_category)
        mediaPlayer.start()

        askspeechinput()
    }
}