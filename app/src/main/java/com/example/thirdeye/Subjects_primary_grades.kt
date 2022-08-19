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
import android.widget.RelativeLayout
import android.widget.Toast
import java.util.*
import kotlin.concurrent.fixedRateTimer

class Subjects_primary_grades : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var buddism : RelativeLayout
    private lateinit var sinhala : RelativeLayout
    private lateinit var enviroment : RelativeLayout
    private lateinit var maths : RelativeLayout
    private lateinit var tamil : RelativeLayout
    private lateinit var english : RelativeLayout
    private lateinit var microphone: Button
    lateinit var textToSpeech: TextToSpeech
    val handler = Handler(Looper.getMainLooper())


    var grade:String = ""
    private val  RQ_SPEECH_REC = 10000
    private var speechResult:String = ""
    lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects_primary_grades)
        grade = intent?.getStringExtra("grade").toString()
        textToSpeech = TextToSpeech(this@Subjects_primary_grades, this@Subjects_primary_grades)

        handler.postDelayed({
            speakOUt()
        }, 10000)
        supportActionBar?.hide()
        Log.d(ContentValues.TAG, "intent grade is: " + grade)
        var mediaPlayer = MediaPlayer()


        buddism = findViewById(R.id.buddism_prm)
        sinhala = findViewById(R.id.sinhala_prm)
        maths = findViewById(R.id.maths_prm)
        tamil = findViewById(R.id.tamil_prm)
        english = findViewById(R.id.english_prm)
        enviroment = findViewById(R.id.env_prm)
        microphone = findViewById(R.id.microphone)

        buddism.setOnClickListener(){
            playContents("buddhism")
        }
        sinhala.setOnClickListener(){
            playContents("sinhala")
        }
        maths.setOnClickListener(){
            playContents("maths")
        }
        tamil.setOnClickListener(){
            playContents("tamil")
        }
        english.setOnClickListener(){
            playContents("english")
        }
        enviroment.setOnClickListener(){
            playContents("enviroment")
        }
        microphone.setOnClickListener {
            askspeechinput()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechResult = result?.get(0).toString()
            if(speechResult == "sinhala"){
                val intent = Intent(this@Subjects_primary_grades, PlayContent::class.java)
                intent.putExtra("subjectname","sinhala")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "english"){
                val intent = Intent(this@Subjects_primary_grades, PlayContent::class.java)
                intent.putExtra("subjectname","english")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "enviroment"){
                val intent = Intent(this@Subjects_primary_grades, PlayContent::class.java)
                intent.putExtra("subjectname","enviroment")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }
            else if(speechResult == "maths"){
                val intent = Intent(this@Subjects_primary_grades, PlayContent::class.java)
                intent.putExtra("subjectname","maths")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "tamil"){
                val intent = Intent(this@Subjects_primary_grades, PlayContent::class.java)
                intent.putExtra("subjectname","tamil")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "buddhism"){
                val intent = Intent(this@Subjects_primary_grades, PlayContent::class.java)
                intent.putExtra("subjectname","buddhism")
                intent.putExtra("grade",grade)
                startActivity(intent)
            } else if (speechResult == "back") {

                    val intent = Intent(this@Subjects_primary_grades, Choose_grade::class.java)
                    startActivity(intent)



            }else if (speechResult == "user room") {

                val intent = Intent(this@Subjects_primary_grades, UserRoom::class.java)
                startActivity(intent)

            }else if (speechResult == "home") {

                val intent = Intent(this@Subjects_primary_grades, Categories::class.java)
                startActivity(intent)

            }
            Log.d(ContentValues.TAG, "speech input: " + result?.get(0).toString())
        }


    }
    private fun playContents(subject:String){
        val subject:String = subject
        val intent = Intent(this@Subjects_primary_grades, PlayContent::class.java)
        intent.putExtra("subjectname",subject)
        intent.putExtra("grade",grade)
        startActivity(intent)
    }
    private fun askspeechinput(){
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this,"speech recog not available", Toast.LENGTH_SHORT).show()
        }else{
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }
    private fun speakOUt() {
        textToSpeech.speak("This is your subject list simply say which subject you want to learn from these... " +
                "sinhala, english, maths, parisaraya, buddhism or tamil ", TextToSpeech.QUEUE_FLUSH, null, null)    }

    private fun speechinput(){


        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.your_category)
        mediaPlayer.start()


    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

        } else {
            Toast.makeText(applicationContext, "Failed text to speech", Toast.LENGTH_SHORT).show()
        }
    }
}