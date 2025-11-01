package com.example.a4_funfactsapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FunFact::class], version = 1, exportSchema = false)
abstract class FunFactDatabase : RoomDatabase() {
    abstract fun funFactDao(): FunFactDao
}