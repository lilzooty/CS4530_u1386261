package com.example.a4_funfactsapplication


import android.app.Application
import androidx.room.Room
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class FunFactsApplication : Application() {

    lateinit var database: FunFactDatabase
        private set

    lateinit var ktorClient: HttpClient
        private set

    lateinit var repository: FunFactRepository
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            FunFactDatabase::class.java,
            "funfacts_database"
        ).build()

        ktorClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        repository = FunFactRepository(database.funFactDao(), ktorClient)
    }

    override fun onTerminate() {
        super.onTerminate()
        ktorClient.close()
    }
}