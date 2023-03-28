package com.example.sleeptracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="item_table")
data class Item (
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    @ColumnInfo(name = "day") val day: Int?,
    @ColumnInfo(name = "duration") val duration: Double?,
    @ColumnInfo(name = "isNap") val isNap : Boolean?
)