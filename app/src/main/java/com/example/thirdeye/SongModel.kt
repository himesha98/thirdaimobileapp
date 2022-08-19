package com.example.thirdeye

class SongModel {
    var file:String? = null
    var authorname:String? = null
    var genre:String? = null
    var songname:String? = null

    constructor(){

    }

    constructor(contentName:String?){
        this.file = file
        this.authorname = authorname
        this.genre = genre
        this.songname = songname

    }
}