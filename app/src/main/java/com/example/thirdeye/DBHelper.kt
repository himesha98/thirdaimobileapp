package com.example.thirdeye

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val queryEdu = ("CREATE TABLE " + CONTENTS + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                GRADE + " TEXT," +
                SUBJECT + " TEXT," + LESSON + " TEXT" + ")")
        val queryMusic = ("CREATE TABLE " + MUSIC + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                GENRE + " TEXT," +
                SONGAUTHOR + " TEXT," + SONGNAME + " TEXT" + ")")
        val queryPodcast = ("CREATE TABLE " + PODCASTS + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                PODCASTNAME + " TEXT," +
                PODCASTAUTHOR + " TEXT," + PODCASTTITLE + " TEXT" + ")")
        val querySeminar = ("CREATE TABLE " + SEMINARS + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                SEMINARTITLE + " TEXT," +
                PRESENTER + " TEXT," + SEMINARCONTENT + " TEXT" + ")")

        val queryBooks = ("CREATE TABLE " + LIBRARY + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                BOOKTYPE + " TEXT," +
                BOOKAUTHOR + " TEXT," + BOOKNAME + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(queryEdu)
        db.execSQL(queryBooks)
        db.execSQL(queryMusic)
        db.execSQL(queryPodcast)
        db.execSQL(querySeminar)

    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + CONTENTS)
        db.execSQL("DROP TABLE IF EXISTS " + MUSIC)
        db.execSQL("DROP TABLE IF EXISTS " + PODCASTS)
        db.execSQL("DROP TABLE IF EXISTS " + SEMINARS)
        db.execSQL("DROP TABLE IF EXISTS " + LIBRARY)

        onCreate(db)
    }

    // This method is for adding data in our database
    fun addName(grade: String, subject: String, lesson: String) {
        val values = ContentValues()
        values.put(GRADE, grade)
        values.put(SUBJECT, subject)
        values.put(LESSON, lesson)
        val db = this.writableDatabase
        db.insert(CONTENTS, null, values)
        db.close()
    }

    fun addMusic(songName: String, author: String, genre: String) {
        val values = ContentValues()
        values.put(SONGNAME, songName)
        values.put(SONGAUTHOR, author)
        values.put(GENRE, genre)
        val db = this.writableDatabase
        db.insert(MUSIC, null, values)
        db.close()
    }

    fun addPodcasts(podcastName: String, podcastauthor: String, podcasttitle: String) {
        val values = ContentValues()
        values.put(PODCASTNAME, podcastName)
        values.put(PODCASTAUTHOR, podcastauthor)
        values.put(PODCASTTITLE, podcasttitle)
        val db = this.writableDatabase
        db.insert(PODCASTS, null, values)
        db.close()
    }

    fun addSeminars(seminartitle: String, presenter: String, seminarcontent: String) {
        val values = ContentValues()
        values.put(SEMINARTITLE, seminartitle)
        values.put(PRESENTER, presenter)
        values.put(SEMINARCONTENT, seminarcontent)
        val db = this.writableDatabase
        db.insert(SEMINARS, null, values)
        db.close()
    }

    fun addBooks(bookName: String, bookauthor: String, booktype: String) {
        val values = ContentValues()
        values.put(BOOKNAME, bookName)
        values.put(BOOKAUTHOR, bookauthor)
        values.put(BOOKTYPE, booktype)
        val db = this.writableDatabase
        db.insert(LIBRARY, null, values)
        db.close()
    }


    // below method is to get
    // all data from our database
    fun getName(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + CONTENTS, null)
    }

    fun getLessons(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + CONTENTS, null)
    }

    fun getMusic(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + MUSIC, null)
    }

    fun getSeminars(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + SEMINARS, null)
    }

    fun getPodcasts(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + PODCASTS, null)
    }

    fun getBooks(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + LIBRARY, null)
    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "DB_4"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for tables names
        val CONTENTS = "subject_contents"
        val MUSIC = "music_contents"
        val PODCASTS = "podcasts"
        val LIBRARY = "library"
        val SEMINARS = "seminars"


        // below is the variable for columns
        val ID_COL = "id"
        val GRADE = "grade"
        val SUBJECT = "subject"
        val LESSON = "lesson"
        val SONGNAME = "lesson"
        val SONGAUTHOR = "songauthor"
        val GENRE = "genre"
        val PODCASTNAME = "podcastname"
        val PODCASTAUTHOR = "podcastauthor"
        val PODCASTTITLE = "podcasttitle"
        val BOOKNAME = "bookname"
        val BOOKAUTHOR = "bookauthor"
        val BOOKTYPE = "booktype"
        val SEMINARTITLE = "seminartitle"
        val PRESENTER = "presenter"
        val SEMINARCONTENT = "seminar_content"


    }
}