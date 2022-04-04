package com.example.roomhomeworkweek5.data

import androidx.room.*
import java.util.*




@Entity(tableName = "donuts_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val day: Long,
    val numberDonuts: Int
)