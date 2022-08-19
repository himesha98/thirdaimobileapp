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
import android.widget.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PlayPodcasts : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private lateinit var play: ImageButton
    private lateinit var contentWrapper: LinearLayout
    private lateinit var seek_bar: SeekBar
    private lateinit var tv_pass: TextView
    private lateinit var tv_due: TextView
    private lateinit var podcastsName: TextView
    private lateinit var contentSec: RelativeLayout
    private lateinit var creatorName: TextView
    private lateinit var imagetest: ImageView
    private lateinit var saveButton: Button

    val handlerspeak = Handler(Looper.getMainLooper())
    private lateinit var Microphone: Button
    lateinit var textToSpeech: TextToSpeech
    private val RQ_SPEECH_REC = 10000
    private var speechResult: String = ""
    private var datasource: String = ""
    private var pause: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_podcasts)
        textToSpeech = TextToSpeech(this@PlayPodcasts, this@PlayPodcasts)

        var datasource: String = ""
        datasource =
            "https://firebasestorage.googleapis.com/v0/b/thirdeye-8cc26.appspot.com/o/contents%2Fmusic%2FShan%20putha%20-Sinhala%20rap.mp3?alt=media&token=db4535c0-2a90-460e-822d-13a6c15b1db1"
        handlerspeak.postDelayed({
            speakOUt()
        }, 10000)
        var pause:Boolean = false

        play = findViewById(R.id.play)
        seek_bar = findViewById(R.id.seek_bar)
        tv_pass = findViewById(R.id.tv_pass)
        tv_due = findViewById(R.id.tv_due)
        podcastsName = findViewById(R.id.podcastName)
        creatorName = findViewById(R.id.creatorName)
        contentWrapper = findViewById(R.id.contentwrapper)
        saveButton = findViewById(R.id.saveButton)

        val database = Firebase.database
        val myRef = database.getReference("contents/podcasts")
        val storage = FirebaseStorage.getInstance()
        val mediaPlayer = MediaPlayer()
        val storageRef =
            storage.getReference()

        saveButton.setOnClickListener {
            val db = DBHelper(this, null)

            val podcastName = "illumination"
            val podcastcreator = "satha and malinda"
            val podcasttitle = "sri lanka"
            if (podcastName != null) {
                if (podcastcreator != null) {
                    db.addPodcasts(podcastName, podcastcreator, podcasttitle)
                }
            }
            Toast.makeText(this, podcastName + " added to database", Toast.LENGTH_LONG).show()

        }
        Microphone.setOnClickListener{
            askspeechinput()
            play.isEnabled = !(play.isEnabled)
        }
        play.setOnClickListener {
            // initializeSeekBar()
            if (!mediaPlayer.isPlaying) {
                if(pause){
                    // mediaPlayer.setDataSource(datasource)
                    //mediaPlayer.prepareAsync()
                    mediaPlayer.seekTo(mediaPlayer.currentPosition)

                    //mediaPlayer = MediaPlayer.create(applicationContext, R.raw.buddism_prm_1)
                    mediaPlayer.start()
                    play.setPadding(30, 30, 30, 30)
                    play.setImageDrawable(getDrawable(R.drawable.pause))
                }else{
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
                Log.d(ContentValues.TAG, "position " +     mediaPlayer.currentPosition)


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

    private fun speakOUt() {
        textToSpeech.speak(
            "This is your podcast player"+ "simply say play for play podcasts and say pause for hold your playback",
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
            if (speechResult == "play" ) {
                playviavoice()
            } else if (speechResult == "pause" || speechResult == "hold") {
                pauseviavoice()
            } else if (speechResult == "back") {
                val intent = Intent(this@PlayPodcasts, Entertainment::class.java)
                startActivity(intent)
            }else if (speechResult == "user room") {
                val intent = Intent(this@PlayPodcasts, UserRoom::class.java)
                startActivity(intent)
            }else if (speechResult == "home") {
                val intent = Intent(this@PlayPodcasts, Categories::class.java)
                startActivity(intent)
            }else if (speechResult == "save") {
                val db = DBHelper(this, null)

                val podcastName = "illumination"
                val podcastcreator = "satha and malinda"
                val podcasttitle = "sri lanka"
                if (podcastName != null) {
                    if (podcastcreator != null) {
                        db.addPodcasts(podcastName, podcastcreator, podcasttitle)
                        textToSpeech.speak(
                            "${podcastcreator}'s + ${podcastName} + saved to user room",
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
    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
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
            Log.d(ContentValues.TAG, "position " + mediaPlayer.currentPosition)


            play.setPadding(20, 10, 10, 10)
            play.setImageResource(R.drawable.play)
            //playBtn.isEnabled = true
            //pauseBtn.isEnabled = false
            //stopBtn.isEnabled = true
            //Toast.makeText(this,"media pause", Toast.LENGTH_SHORT).show()
        }
    }
    // Creating an extension property to get media player current position in seconds
    private val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }
}