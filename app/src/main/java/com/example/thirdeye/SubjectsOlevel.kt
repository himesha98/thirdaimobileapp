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

class SubjectsOlevel : AppCompatActivity(), TextToSpeech.OnInitListener  {

    private lateinit var buddism : RelativeLayout
    private lateinit var sinhala : RelativeLayout
    private lateinit var science : RelativeLayout
    private lateinit var maths : RelativeLayout
    private lateinit var tamil : RelativeLayout
    private lateinit var english : RelativeLayout
    private lateinit var ict : RelativeLayout
    private lateinit var geography : RelativeLayout
    private lateinit var civics : RelativeLayout
    private lateinit var history : RelativeLayout
    private lateinit var health : RelativeLayout
    private lateinit var commerce : RelativeLayout
    var grade:String = ""
    private lateinit var microphone: Button
    lateinit var textToSpeech: TextToSpeech
    val handler = Handler(Looper.getMainLooper())
    private val  RQ_SPEECH_REC = 10000
    private var speechResult:String = ""
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects_olevel)
        supportActionBar?.hide()
        grade= intent?.getStringExtra("grade").toString()
        var mediaPlayer = MediaPlayer()
        textToSpeech = TextToSpeech(this@SubjectsOlevel, this@SubjectsOlevel)

        handler.postDelayed({
            speakOUt()
        }, 10000)

        buddism = findViewById(R.id.buddism_ol)
        sinhala = findViewById(R.id.sinhala_ol)
        maths = findViewById(R.id.maths_ol)
        civics = findViewById(R.id.civics_ol)
        science = findViewById(R.id.science_ol)
        geography = findViewById(R.id.geography_ol)
        history = findViewById(R.id.history_ol)
        tamil = findViewById(R.id.tamil_ol)
        ict = findViewById(R.id.ict_ol)
        english = findViewById(R.id.english_ol)
        health = findViewById(R.id.health_ol)
        commerce = findViewById(R.id.commerce_ol)
        microphone = findViewById(R.id.microphone)

        buddism.setOnClickListener(){
            playContents("buddhism")
        }
        sinhala.setOnClickListener(){
            playContents("sinhala")
        }
        english.setOnClickListener(){
            playContents("english")
        }
        maths.setOnClickListener(){
            playContents("maths")
        }
        science.setOnClickListener(){
            playContents("science")
        }
        civics.setOnClickListener(){
            playContents("civics")
        }
        geography.setOnClickListener(){
            playContents("geography")
        }
        history.setOnClickListener(){
            playContents("history")
        }
        tamil.setOnClickListener(){
            playContents("tamil")
        }
        ict.setOnClickListener(){
            playContents("ict")
        }
        health.setOnClickListener(){
            playContents("health")
        }
        commerce.setOnClickListener(){
            playContents("health")
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
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","sinhala")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "english"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","english")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "science"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","science")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }
            else if(speechResult == "maths"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","maths")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "tamil"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","tamil")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "buddhism"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","buddhism")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "health"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","health")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "civics"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","civics")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "geography"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","geography")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "tamil"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","tamil")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "ict"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","ict")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if(speechResult == "commerce"){
                val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
                intent.putExtra("subjectname","cemmerce")
                intent.putExtra("grade",grade)
                startActivity(intent)
            }else if (speechResult == "back") {

                    val intent = Intent(this@SubjectsOlevel, Categories::class.java)
                    startActivity(intent)



            }else if (speechResult == "user room") {

                val intent = Intent(this@SubjectsOlevel, UserRoom::class.java)
                startActivity(intent)

            }else if (speechResult == "home") {

                val intent = Intent(this@SubjectsOlevel, Categories::class.java)
                startActivity(intent)

            }
            Log.d(ContentValues.TAG, "speech input: " + result?.get(0).toString())
        }


    }
    private fun playContents(subject:String){

        val intent = Intent(this@SubjectsOlevel, PlayContent::class.java)
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
                "sinhala, english, maths, science, history, geography, information technology ", TextToSpeech.QUEUE_FLUSH, null, null)
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

        } else {
            Toast.makeText(applicationContext, "Failed text to speech", Toast.LENGTH_SHORT).show()
        }
    }
}