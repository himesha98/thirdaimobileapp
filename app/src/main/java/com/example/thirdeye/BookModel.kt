package com.example.thirdeye

class BookModel {
    var file:String? = null
    var bookauthor:String? = null
    var booktype:String? = null
    var booktitle:String? = null

    constructor(){

    }

    constructor(contentName:String?){
        this.file = file
        this.bookauthor = bookauthor
        this.booktype = booktype
        this.booktitle = booktitle

    }

}