package com.example.runningtracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val RESULTS = "RESULTS"
private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private val running_activities = mutableListOf<Running>()
    private lateinit var run_activityRecyclerView: RecyclerView
    private lateinit var addButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    }
                }
            }

        }

    }
}