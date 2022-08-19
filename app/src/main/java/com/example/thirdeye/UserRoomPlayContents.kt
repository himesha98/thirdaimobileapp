package com.example.thirdeye

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class UserRoomPlayContents : AppCompatActivity(),TextToSpeech.OnInitListener {

    private lateinit var contentRecyclerView: RecyclerView
    //private lateinit var contentList:ArrayList<UerRoomEduContentModel>
    private lateinit var adapter: UserRoomContentAdapter
    //private lateinit var cursor:Cursor

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

    var columnOne = ""
    var columnTwo = ""
    var columnThree = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_room_play_contents)
        supportActionBar?.hide()
        textToSpeech = TextToSpeech(this@UserRoomPlayContents, this@UserRoomPlayContents)
        handlerspeak.postDelayed({
            speakOUt()
        }, 10000)
        val contentList: ArrayList<UerRoomEduContentModel> = ArrayList()
        adapter = UserRoomContentAdapter(this,contentList)
        val category = intent.getStringExtra("category")
        Log.d(ContentValues.TAG, "user room category: " + category)

        contentRecyclerView = findViewById(R.id.userRoomRecyclerView)
        contentRecyclerView.layoutManager = LinearLayoutManager(this)
        contentRecyclerView.adapter = adapter




        val db = DBHelper(this, null)
        var content: UerRoomEduContentModel
        var cursor: Cursor? = db.getName()
        if (category=="Lessons"){
            cursor = db.getLessons()!!
        }else if(category=="Seminars"){
            cursor = db.getSeminars()!!
        }else if(category=="Music"){
            cursor = db.getMusic()!!
        }else if(category=="Podcasts"){
            cursor = db.getPodcasts()!!
        }else if(category=="Library"){
            cursor = db.getBooks()!!
        }

        cursor!!.moveToFirst()
        while (cursor.moveToNext()) {
            if (cursor.getCount() > 0) {
                Log.d(
                    ContentValues.TAG,
                    "cursor text okay"
                )

                content = UerRoomEduContentModel()
                content.columnone = cursor.getString(1)
                content.columntwo = cursor.getString(2)
                content.columnthree = cursor.getString(3)

                columnOne=cursor.getString(1)
                columnTwo=cursor.getString(2)
                columnThree=cursor.getString(3)
                contentList.add(content);

                Log.d(
                    ContentValues.TAG,
                    "columnthree: " + content.columnthree
                )
                Log.d(
                    ContentValues.TAG,
                    "columnone: " + content.columnone
                )

            }
        }

        adapter.notifyDataSetChanged()

        Log.d(
            ContentValues.TAG,
            "content list: " + contentList
        )
        Log.d(
            ContentValues.TAG,
            "cursor: " + cursor
        )

        cursor.close();
        db.close();
        play = findViewById(R.id.play)
        seek_bar = findViewById(R.id.seek_bar)
        tv_pass = findViewById(R.id.tv_pass)
        tv_due = findViewById(R.id.tv_due)
        contentText = findViewById(R.id.contentName)
        subjectText = findViewById(R.id.subjectName)
        contentWrapper = findViewById(R.id.contentwrapper)
        contentSec = findViewById(R.id.contentsection)
        subjectText = findViewById(R.id.subjectName)
        //saveButton = findViewById(R.id.saveButton)
        Microphone = findViewById(R.id.microphone)

        datasource =
            "https://firebasestorage.googleapis.com/v0/b/thirdeye-8cc26.appspot.com/o/contents%2FGrade%201%2FSinhala%2Flesson1%2Faudiotest.mp3?alt=media&token=df342bb9-77f0-410f-a6f8-8f361d677a6e"
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
                 val intent = Intent(this@UserRoomPlayContents, UserRoom::class.java)
                    startActivity(intent)



            }else if (speechResult == "user room" || speechResult == "user"|| speechResult == "room") {

                val intent = Intent(this@UserRoomPlayContents, UserRoom::class.java)
                startActivity(intent)

            }else if (speechResult == "home") {

                val intent = Intent(this@UserRoomPlayContents, Categories::class.java)
                startActivity(intent)

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
    private fun speakOUt() {
        textToSpeech.speak(
            "This is your media player simply say play for play contents and say pause for hold your playback",
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
}