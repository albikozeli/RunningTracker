package com.example.runningtracker

import android.app.Application

class RunningApplication: Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}