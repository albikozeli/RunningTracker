package com.example.runningtracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.slider.Slider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class EnterRunning: AppCompatActivity(){
    private lateinit var activityEditText: EditText
    private lateinit var distanceEditText: EditText
    private lateinit var durationEditText: EditText
    private lateinit var experienceSlider: Slider
    private lateinit var recordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)

        //Find add_activity elements
        activityEditText = findViewById(R.id.enter_activity)
        distanceEditText = findViewById(R.id.enter_distance)
        durationEditText = findViewById(R.id.enter_duration)
        experienceSlider = findViewById(R.id.slider)
        recordButton = findViewById(R.id.record_activity)

        recordButton.setOnClickListener{
            val activity = activityEditText.getText().toString()
            val distance = distanceEditText.getText().toString().toFloat()
            val duration = durationEditText.getText().toString().toFloat()
            val experience = experienceSlider.value.toInt()

            val new_activity = Running(activity,distance,duration,experience)

            lifecycleScope.launch(IO) {
                (application as RunningApplication).db.runningDao().insert(
                    RunningEntity(new_activity.activity,new_activity.distance,new_activity.duration, new_activity.experience)
                )
            }

            this.finish()
        }
    }
}