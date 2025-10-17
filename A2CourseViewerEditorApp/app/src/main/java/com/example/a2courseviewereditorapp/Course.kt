package com.example.a2courseviewereditorapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val department: String,
    val courseNumber: String,
    val location: String
)