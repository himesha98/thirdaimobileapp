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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PlayMusic : AppCompatActivity(), TextToSpeech.OnInitListener {
    val mediaPlayer = MediaPlayer()
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private lateinit var play: ImageButton
    private lateinit var contentWrapper: LinearLayout
    private lateinit var seek_bar: SeekBar
    private lateinit var tv_pass: TextView
    private lateinit var tv_due: TextView
    private lateinit var songName: TextView
    private lateinit var contentSec: RelativeLayout
    val handlerspeak = Handler(Looper.getMainLooper())
    private lateinit var Microphone: Button
    lateinit var textToSpeech: TextToSpeech
    private val RQ_SPEECH_REC = 10000
    private var speechResult: String = ""
    private var datasource: String = ""
    private var pause: Boolean = false

    private lateinit var artistName: TextView
    private lateinit var imagetest: ImageView
    private lateinit var saveButton: Button
    private lateinit var subjectText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_play_music)

        textToSpeech = TextToSpeech(this@PlayMusic, this@PlayMusic)

        datasource =
            "https://firebasestorage.googleapis.com/v0/b/thirdeye-8cc26.appspot.com/o/contents%2Fmusic%2FShan%20putha%20-Sinhala%20rap.mp3?alt=media&token=db4535c0-2a90-460e-822d-13a6c15b1db1"
        handlerspeak.postDelayed({
            speakOUt()
        }, 10000)

        var pause: Boolean = false

        play = findViewById(R.id.play)
        seek_bar = findViewById(R.id.seek_bar)
        tv_pass = findViewById(R.id.tv_pass)
        tv_due = findViewById(R.id.tv_due)
        songName = findViewById(R.id.songName)
        artistName = findViewById(R.id.artistName)
        contentWrapper = findViewById(R.id.contentwrapper)
        saveButton = findViewById(R.id.saveButton)
        Microphone = findViewById(R.id.microphone)


        val database = Firebase.database
        val myRef = database.getReference("Music/Rap/shan putha/sinhala rap")
        val storage = FirebaseStorage.getInstance()
        //val mediaPlayer = MediaPlayer()
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //val value = snapshot.getValue<String>()
                Log.d(ContentValues.TAG, "Value is: " + snapshot)

                val content = snapshot.getValue(SongModel::class.java)
                Log.d(ContentValues.TAG, "file is: " + content?.file)
                Log.d(ContentValues.TAG, "song is: " + content?.songname)
                Log.d(ContentValues.TAG, "genre is: " + content?.genre)
                songName.setText(content?.songname)
                artistName.setText(content?.authorname)


                datasource =
                    "https://firebasestorage.googleapis.com/v0/b/thirdeye-8cc26.appspot.com/o/contents%2Fmusic%2FShan%20putha%20-Sinhala%20rap.mp3?alt=media&token=db4535c0-2a90-460e-822d-13a6c15b1db1"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })
        val storageRef =
            storage.getReference()

        saveButton.setOnClickListener {
            val db = DBHelper(this, null)

            val songName = "Shan putha rap"
            val artistName = "shan putha"
            val genre = "rap "
            if (songName != null) {
                if (artistName != null) {
                    db.addMusic(songName, artistName, genre)
                }
            }
            Toast.makeText(this, songName + " added to database", Toast.LENGTH_LONG).show()

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
                Log.d(ContentValues.TAG, "position " + mediaPlayer.currentPosition)


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
        Microphone.setOnClickListener {
            askspeechinput()
            play.isEnabled = !(play.isEnabled)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechResult = result?.get(0).toString()
            if (speechResult == "play") {
                playviavoice()
            } else if (speechResult == "pause" || speechResult == "hold") {
                pauseviavoice()
            } else if (speechResult == "back") {
                val intent = Intent(this@PlayMusic, Entertainment::class.java)
                startActivity(intent)
            } else if (speechResult == "user room" || speechResult == "user"|| speechResult == "room") {
                val intent = Intent(this@PlayMusic, UserRoom::class.java)
                startActivity(intent)
            }else if (speechResult == "home") {
                val intent = Intent(this@PlayMusic, Categories::class.java)
                startActivity(intent)
            }else if (speechResult == "save") {
                val db = DBHelper(this, null)

                val songName = "Shan putha rap"
                val artistName = "shan putha"
                val genre = "rap "
                if (songName != null) {
                    if (artistName != null) {
                        db.addMusic(songName, artistName, genre)
                        textToSpeech.speak(
                            "${artistName}'s + ${songName} + saved to user room",
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

    private fun pauseviavoice() {
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

    private fun speakOUt() {
        textToSpeech.speak(
            "This is your media player" + "simply say play for play contents and say pause for hold your playback",
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
}