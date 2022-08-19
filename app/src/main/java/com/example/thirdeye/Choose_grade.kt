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


import com.example.thirdeye.R.id.grade_1
import com.example.thirdeye.R.id.grade_2
import com.example.thirdeye.R.id.grade_3
import com.example.thirdeye.R.id.grade_4
import com.example.thirdeye.R.id.grade_5
import com.example.thirdeye.R.id.grade_6
import com.example.thirdeye.R.id.grade_7
import com.example.thirdeye.R.id.grade_8
import com.example.thirdeye.R.id.grade_9
import com.example.thirdeye.R.id.grade_10
import com.example.thirdeye.R.id.grade_11
import java.util.*
import kotlin.concurrent.fixedRateTimer


class Choose_grade : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var grade1: RelativeLayout
    private lateinit var grade2: RelativeLayout
    private lateinit var grade3: RelativeLayout
    private lateinit var grade4: RelativeLayout
    private lateinit var grade5: RelativeLayout
    private lateinit var grade6: RelativeLayout
    private lateinit var grade7: RelativeLayout
    private lateinit var grade8: RelativeLayout
    private lateinit var grade9: RelativeLayout
    private lateinit var grade10: RelativeLayout
    private lateinit var grade11: RelativeLayout
    private lateinit var microphone: Button
    private val  RQ_SPEECH_REC = 10000
    private var speechResult:String = ""
    lateinit var mediaPlayer: MediaPlayer
    lateinit var textToSpeech: TextToSpeech
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_grade)
        supportActionBar?.hide()
        var mediaPlayer = MediaPlayer()
        textToSpeech = TextToSpeech(this@Choose_grade, this@Choose_grade)

        handler.postDelayed({
            speakOUt()
        }, 10000)


        grade1 = findViewById(R.id.grade_1)
        grade2 = findViewById(R.id.grade_2)
        grade3 = findViewById(R.id.grade_3)
        grade4 = findViewById(R.id.grade_4)
        grade5 = findViewById(R.id.grade_5)
        grade6 = findViewById(R.id.grade_6)
        grade7 = findViewById(R.id.grade_7)
        grade8 = findViewById(R.id.grade_8)
        grade9 = findViewById(R.id.grade_9)
        grade10 = findViewById(R.id.grade_10)
        grade11 = findViewById(R.id.grade_11)
        microphone = findViewById(R.id.microphone)



        grade1.setOnClickListener{
            subjects(1)

        }
        grade2.setOnClickListener{
            subjects(2)
        }
        grade3.setOnClickListener{
            subjects(3)
        }
        grade4.setOnClickListener{
            subjects(4)
        }
        grade5.setOnClickListener{
            subjects(5)
        }
        grade6.setOnClickListener{
            subjects(6)
        }
        grade7.setOnClickListener{
            subjects(7)
        }
        grade8.setOnClickListener{
            subjects(8)
        }
        grade9.setOnClickListener{
            subjects(9)
        }
        grade10.setOnClickListener{
            subjects(10)
        }
        grade11.setOnClickListener{
            subjects(11)
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
            if(speechResult == "grade one" || speechResult == "one" || speechResult == "grade 1"){
                val intent = Intent(this@Choose_grade, Subjects_primary_grades::class.java)

                intent.putExtra("grade","1")
                startActivity(intent)
            }else if(speechResult == "grade two" || speechResult == "two" || speechResult == "grade 2"){
                val intent = Intent(this@Choose_grade, Subjects_primary_grades::class.java)

                intent.putExtra("grade","2")
                startActivity(intent)
            }else if(speechResult == "grade three" || speechResult == "three" || speechResult == "grade 3"){
                val intent = Intent(this@Choose_grade, Subjects_primary_grades::class.java)

                intent.putExtra("grade","3")
                startActivity(intent)
            }else if(speechResult == "grade four" || speechResult == "four" || speechResult == "grade 4"){
                val intent = Intent(this@Choose_grade, Subjects_primary_grades::class.java)

                intent.putExtra("grade","4")
                startActivity(intent)
            }else if(speechResult == "grade five" || speechResult == "five" || speechResult == "grade 5"){
                val intent = Intent(this@Choose_grade, Subjects_primary_grades::class.java)

                intent.putExtra("grade","5")
                startActivity(intent)
            }else if(speechResult == "grade six" || speechResult == "six" || speechResult == "grade 6"){
                val intent = Intent(this@Choose_grade, SubjectsMidGrades::class.java)

                intent.putExtra("grade","6")
                startActivity(intent)
            }else if(speechResult == "grade seven" || speechResult == "seven" || speechResult == "grade 7"){
                val intent = Intent(this@Choose_grade, SubjectsMidGrades::class.java)

                intent.putExtra("grade","7")
                startActivity(intent)
            }else if(speechResult == "grade eight" || speechResult == "three" || speechResult == "grade 3"){
                val intent = Intent(this@Choose_grade, SubjectsMidGrades::class.java)

                intent.putExtra("grade","3")
                startActivity(intent)
            }else if(speechResult == "grade nine" || speechResult == "nine" || speechResult == "grade 9"){
                val intent = Intent(this@Choose_grade, SubjectsMidGrades::class.java)

                intent.putExtra("grade","9")
                startActivity(intent)
            }else if(speechResult == "grade ten" || speechResult == "ten" || speechResult == "grade 10"){
                val intent = Intent(this@Choose_grade, SubjectsOlevel::class.java)

                intent.putExtra("grade","10")
                startActivity(intent)
            }else if(speechResult == "grade eleven" || speechResult == "eleven" || speechResult == "grade 11"){
                val intent = Intent(this@Choose_grade, SubjectsOlevel::class.java)

                intent.putExtra("grade","11")
                startActivity(intent)
            }else if (speechResult == "user room" || speechResult == "user"|| speechResult == "room") {

                val intent = Intent(this@Choose_grade, UserRoom::class.java)
                startActivity(intent)

            }else if (speechResult == "home") {

                val intent = Intent(this@Choose_grade, Categories::class.java)
                startActivity(intent)

            }
            Log.d(ContentValues.TAG, "speech input: " + result?.get(0).toString())
        }

    }
    private fun speakOUt() {
        textToSpeech.speak("choose you grade you can say it for an example like grade one or one", TextToSpeech.QUEUE_FLUSH, null, null)
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

        } else {
            Toast.makeText(applicationContext, "Failed text to speech", Toast.LENGTH_SHORT).show()
        }
    }
    private fun subjects(id :Int){
        val id:Int = id
        if(id == 1 || id == 2 || id == 3 || id == 4 || id == 5) {
            val intent = Intent(this@Choose_grade, Subjects_primary_grades::class.java)

            intent.putExtra("grade",id.toString())
            startActivity(intent)
        }else if(id == 6 || id == 7 || id == 8 || id == 9){
            val intent = Intent(this@Choose_grade, SubjectsMidGrades::class.java)
            intent.putExtra("grade",id.toString())
            startActivity(intent)

        }else if (id == 10 || id == 11){
            val intent = Intent(this@Choose_grade, SubjectsOlevel::class.java)
            intent.putExtra("grade",id.toString())
            startActivity(intent)

        }
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





}