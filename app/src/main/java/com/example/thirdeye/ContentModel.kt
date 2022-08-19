package com.example.thirdeye

class ContentModel{
  var file:String? = null
  var grade:String? = null
  var lessonNumber:String? = null
  var subject:String? = null

  constructor(){

  }

  constructor(file:String?,grade:String?,lessonNumber:String?,subject:String?){
    this.file = file
    this.lessonNumber = lessonNumber
    this.grade = grade
    this.subject = subject

  }
}
    