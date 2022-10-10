package com.example.runningtracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

const val RESULTS = "RESULTS"
private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private val running_activities = mutableListOf<Running>()
    private lateinit var run_activityRecyclerView: RecyclerView
    private lateinit var addButton: Button
    private lateinit var expTextView: TextView
    private lateinit var disTextView: TextView
    private lateinit var durTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expTextView = findViewById(R.id.experience_avg)
        disTextView = findViewById(R.id.distance_avg)
        durTextView = findViewById(R.id.duration_avg)
        run_activityRecyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.button)

        val runAdapter = RunningAdapter(this, running_activities)
        run_activityRecyclerView.adapter = runAdapter

        lifecycleScope.launch {
            (application as RunningApplication).db.runningDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Running(
                        entity.activity,
                        entity.distance,
                        entity.duration,
                        entity.experience
                    )
                }.also { mappedList ->
                    running_activities.clear()
                    running_activities.addAll(mappedList)
                    runAdapter.notifyDataSetChanged()
                    val triple = calculate_metrics(running_activities)
                    expTextView.text = "Average experience: " + triple.first + "/10"
                    disTextView.text = "Average distance: " + triple.second + " miles"
                    durTextView.text = "Average duration: " + triple.third + " min"
                }
            }
        }

        run_activityRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            run_activityRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        addButton.setOnClickListener{
            val intent = Intent(this, EnterRunning::class.java)
            this.startActivity(intent)

            lifecycleScope.launch {
                (application as RunningApplication).db.runningDao().getAll().collect { databaseList ->
                    databaseList.map { entity ->
                        Running(
                            entity.activity,
                            entity.distance,
                            entity.duration,
                            entity.experience
                        )
                    }.also { mappedList ->
                        running_activities.clear()
                        running_activities.addAll(mappedList)
                        runAdapter.notifyDataSetChanged()
                        val triple = calculate_metrics(running_activities)
                    }
                }
            }

        }

    }

    fun calculate_metrics(run_act:List<Running>):Triple<String,String,String>{
        val dec = DecimalFormat("#.0")
        val count = run_act.size
        var exp =0.0
        var distance = 0.0
        var duration = 0.0
        for (activity in run_act){
            exp += activity.experience
            distance += activity.distance
            duration += activity.duration
        }
        return Triple(dec.format(exp/count), dec.format(distance/count), dec.format(duration/count))
    }
}