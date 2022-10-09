package com.example.runningtracker

data class Running (
    val activity:String,
    val distance:Float,
    val duration:Float,
    val experience:Int
): java.io.Serializable