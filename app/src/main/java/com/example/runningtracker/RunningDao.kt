package com.example.runningtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RunningDao{
    @Query("SELECT * FROM running_table")
    fun getAll(): Flow<List<RunningEntity>>

    @Insert
    fun insert(running_activity: RunningEntity)
}