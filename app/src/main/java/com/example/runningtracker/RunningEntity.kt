package com.example.runningtracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class RunningEntity(
    @ColumnInfo(name = "activity") val activity: String,
    @ColumnInfo(name = "distance") val distance: Float,
    @ColumnInfo(name = "duration") val duration: Float,
    @ColumnInfo(name = "experience") val experience: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
//    @ColumnInfo(name = "mediaImageUrl") val mediaImageUrl: String?
)