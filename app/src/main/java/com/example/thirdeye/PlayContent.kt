package com.example.thirdeye

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.util.*


class PlayContent : AppCompatActivity(), TextToSpeech.OnInitListener {
    //lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    val mediaPlayer = MediaPlayer()
    val handlerspeak = Handler(Looper.getMainLooper())

    private lateinit var play: ImageButton
    private lateinit var contentWrapper: LinearLayout
    private lateinit var seek_bar: SeekBar
    private lateinit var tv_pass: TextView
    private lateinit var tv_due: TextView
    private lateinit var contentText: TextView
    private lateinit var contentSec: RelativeLayout
    private lateinit var subjectText: TextView
    private lateinit var imagetest: ImageView
    private lateinit var saveButton: Button
    private lateinit var Subject: TextView
    private lateinit var Grade: TextView
    private lateinit var Lesson: TextView
    private lateinit var Microphone: Button
    lateinit var textToSpeech: TextToSpeech
    private val RQ_SPEECH_REC = 10000
    private var speechResult: String = ""
    private var datasource: String = ""
    private var pause: Boolean = false


    var grade:String = ""
    var subjectname:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_play_content)
         subjectname = intent.getStringExtra("subjectname").toString()
         grade = intent.getStringExtra("grade").toString()

        textToSpeech = TextToSpeech(this@PlayContent, this@PlayContent)

        datasource =
            "https://firebasestorage.googleapis.com/v0/b/thirdeye-8cc26.appspot.com/o/contents%2FGrade%201%2FSinhala%2Flesson1%2Faudiotest.mp3?alt=media&token=df342bb9-77f0-410f-a6f8-8f361d677a6e"
        handlerspeak.postDelayed({
            speakOUt()
        }, 10000)

        Log.d(TAG, "intent grade is: " + grade)
        Log.d(TAG, "intent sub is: " + subjectname)
        //mediaPlayer = MediaPlayer.create(applicationContext, R.raw.buddism_prm_1)
        play = findViewById(R.id.play)
        seek_bar = findViewById(R.id.seek_bar)
        tv_pass = findViewById(R.id.tv_pass)
        tv_due = findViewById(R.id.tv_due)
        contentText = findViewById(R.id.contentName)
        subjectText = findViewById(R.id.subjectName)
        contentWrapper = findViewById(R.id.contentwrapper)
        contentSec = findViewById(R.id.contentsection)
        subjectText = findViewById(R.id.subjectName)
        saveButton = findViewById(R.id.saveButton)
        Microphone = findViewById(R.id.microphone)


        val database = Firebase.database
        val myRef = database.getReference("contents/Education/${grade}/${subjectname}/lesson1/")
        val storage = FirebaseStorage.getInstance()
        val mediaPlayer = MediaPlayer()
        val storageRef =
            storage.getReference()  // Create a reference with an initial file path and name

        saveButton.setOnClickListener {
            val db = DBHelper(this, null)

            val grade = grade
            val subject = subjectname
            val lesson = "lesson 1"
            if (grade != null) {
                if (subject != null) {
                    db.addName(grade, subject, lesson)
                }
            }
            Toast.makeText(this, subject + " added to database", Toast.LENGTH_LONG).show()


            // at last we close our cursor

        }
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "Value is: " + snapshot.value)

                    val content = snapshot.getValue(ContentModel::class.java)
                    Log.d(TAG, "file is: " + content?.file)
                    Log.d(TAG, "subject is: " + content?.subject)
                    Log.d(TAG, "grade is: " + content?.grade)
                    contentText.setText(content?.subject)
                    subjectText.setText(content?.grade)
                    // Create a storage reference from our app
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })


        contentWrapper.setOnClickListener {

            Log.d(TAG, "clicked")

            try {
                //val pathReference = storage.getReferenceFromUrl("gs://thirdeye-8cc26.appspot.com/contents/Grade 11/Maths/lesson3/audiotest.mp3")
                val pathReference = storage.getReferenceFromUrl(
                    "https://firebasestorage.googleapis.com/v0/b/thirdeye-8cc26.appspot.com/o/contents%2FGrade%2011%2FMaths%2Flesson3%2Faudiotest.mp3?alt=media&token=0f80721b-07a3-43d5-8541-d24b6a28361c"
                )
                //val pathReference = storageRef.child("contents/Grade 11/Maths/lesson3/audiotest.mp3")
                var islandRef = storageRef.child("files/audiotest.mp3")

                Log.d(TAG, "PATH IS" + pathReference.toString())
                val localFile = File.createTempFile("audio", "mpeg")

                islandRef.getFile(localFile).addOnSuccessListener {
                    Log.d(TAG, "file created" + it)
                    // val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    //imagetest.setImageBitmap(bitmap)
                    datasource =
                        "https://firebasestorage.googleapis.com/v0/b/thirdeye-8cc26.appspot.com/o/files%2Faudiotest.mp3?alt=media&token=044a9f3c-347a-40cc-bc18-efad1fc76433"
                    mediaPlayer.setDataSource(datasource)
                    mediaPlayer.prepare()


                }.addOnFailureListener {
                    Log.d(TAG, "file error" + it.toString())
                }



            } catch (exception: Exception) {
                Log.d(TAG, "ERROR IS " + exception.toString())

            } catch (e: IllegalArgumentException) {
                e.printStackTrace();
            } catch (e: SecurityException) {
                e.printStackTrace();
            } catch (e: IllegalStateException) {
                e.printStackTrace();
            } catch (e: IOException) {
                e.printStackTrace();
            }
            //Log.d("path", pathReference)
            contentWrapper.setBackgroundResource(R.drawable.singlegradeback)

        }

        Microphone.setOnClickListener{
            askspeechinput()
            play.isEnabled = !(play.isEnabled)
        }
        play.setOnClickListener {
            // initializeSeekBar()
            if (!mediaPlayer.isPlaying) {
                if (pause) {
                    // mediaPlayer.setDataSource(datasource)
                    //mediaPlayer.prepareAsync()
                    mediaPlayer.seekTo(mediaPlayer.currentPosition)

                    //mediaPlayer = MediaPlayer.create(applicationContext, R.raw.buddism_prm_1)
                    mediaPlayer.start()
                    play.setPadding(30, 30, 30, 30)
                    play.setImageDrawable(getDrawable(R.drawable.pause))
                } else {
                    mediaPlayer.setDataSource(datasource)
                    mediaPlayer.prepare()
                    //mediaPlayer = MediaPlayer.create(applicationContext, R.raw.buddism_prm_1)
                    mediaPlayer.start()
                    play.setPadding(30, 30, 30, 30)
                    play.setImageDrawable(getDrawable(R.drawable.pause))
                }


                // Toast.makeText(this,"media playing", Toast.LENGTH_SHORT).show()

            } else if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                pause = true
                Log.d(TAG, "position " + mediaPlayer.currentPosition)


                play.setPadding(20, 10, 10, 10)
                play.setImageResource(R.drawable.play)
                //playBtn.isEnabled = true
                //pauseBtn.isEnabled = false
                //stopBtn.isEnabled = true
                //Toast.makeText(this,"media pause", Toast.LENGTH_SHORT).show()
            }

            seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    if (b) {
                        mediaPlayer.seekTo(i * 1000)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })
        }

    }

    private fun playviavoice() {

        if (!mediaPlayer.isPlaying) {
            if (pause) {
                // mediaPlayer.setDataSource(datasource)
                //mediaPlayer.prepareAsync()
                mediaPlayer.seekTo(mediaPlayer.currentPosition)

                //mediaPlayer = MediaPlayer.create(applicationContext, R.raw.buddism_prm_1)
                mediaPlayer.start()
                play.setPadding(30, 30, 30, 30)
                play.setImageDrawable(getDrawable(R.drawable.pause))
            } else {
                mediaPlayer.setDataSource(datasource)
                mediaPlayer.prepare()
                //mediaPlayer = MediaPlayer.create(applicationContext, R.raw.buddism_prm_1)
                mediaPlayer.start()
                play.setPadding(30, 30, 30, 30)
                play.setImageDrawable(getDrawable(R.drawable.pause))
            }
        }
    }

    private fun pauseviavoice(){
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            pause = true
            Log.d(TAG, "position " + mediaPlayer.currentPosition)


            play.setPadding(20, 10, 10, 10)
            play.setImageResource(R.drawable.play)
            //playBtn.isEnabled = true
            //pauseBtn.isEnabled = false
            //stopBtn.isEnabled = true
            //Toast.makeText(this,"media pause", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOUt() {
        textToSpeech.speak(
            "This is your media player"+"your grade is "+{grade}+"your subject is"+{subjectname}+ "simply say play for play contents and say pause for hold your playback",
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechResult = result?.get(0).toString()
            if (speechResult == "play" ) {
                playviavoice()
            } else if (speechResult == "pause" || speechResult == "hold") {
                pauseviavoice()
            } else if (speechResult == "back") {
                if( grade == "1" || grade =="2" || grade =="3" || grade =="4" || grade =="5" ){
                    val intent = Intent(this@PlayContent, Subjects_primary_grades::class.java)
                    intent.putExtra("grade",grade)
                    startActivity(intent)

                }else if (grade == "6" || grade =="7" || grade =="8" || grade =="9"){
                    val intent = Intent(this@PlayContent, SubjectsMidGrades::class.java)
                    intent.putExtra("grade",grade)
                    startActivity(intent)

                }else if (grade == "10" || grade =="11"){
                    val intent = Intent(this@PlayContent, SubjectsOlevel::class.java)
                    intent.putExtra("grade",grade)
                    startActivity(intent)

                }

            }else if (speechResult == "user room" || speechResult == "user"|| speechResult == "room") {

                val intent = Intent(this@PlayContent, UserRoom::class.java)
                startActivity(intent)

            }else if (speechResult == "home") {

                val intent = Intent(this@PlayContent, Categories::class.java)
                startActivity(intent)

            }else if (speechResult == "save") {

                val db = DBHelper(this, null)

                val grade = grade
                val subject = subjectname
                val lesson = "lesson 1"
                if (grade != null) {
                    if (subject != null) {
                        db.addName(grade, subject, lesson)
                        textToSpeech.speak(
                            "${grade} + ${subject}+${lesson}+ saved to user room.",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }
                }

            }
            Log.d(ContentValues.TAG, "speech input: " + result?.get(0).toString())
        }


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

    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer.currentSeconds

            tv_pass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            tv_due.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }




    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }

    // Creating an extension property to get media player current position in seconds
    private val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

        } else {
            Toast.makeText(applicationContext, "Failed text to speech", Toast.LENGTH_SHORT).show()
        }
    }
}




