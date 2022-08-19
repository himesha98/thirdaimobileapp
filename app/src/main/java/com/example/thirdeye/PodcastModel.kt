package com.example.thirdeye

class PodcastModel {
    var file:String? = null
    var podcastcreator:String? = null
    var podcastname:String? = null
    var podcasttitle:String? = null

    constructor(){

    }

    constructor(contentName:String?){
        this.file = file
        this.podcastcreator = podcastcreator
        this.podcastname = podcastname
        this.podcasttitle = podcasttitle

    }
}