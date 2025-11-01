package com.example.a4_funfactsapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "fun_facts")
data class FunFact(
    val text: String,

    @PrimaryKey(autoGenerate = true)
    @kotlinx.serialization.Transient
    val id: Long = 0
)