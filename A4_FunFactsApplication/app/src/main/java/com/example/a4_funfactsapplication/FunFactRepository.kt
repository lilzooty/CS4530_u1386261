package com.example.a4_funfactsapplication

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow

class FunFactRepository(
    private val funFactDao: FunFactDao,
    private val ktorClient: HttpClient
) {

    fun getAllFacts(): Flow<List<FunFact>> {
        return funFactDao.getAllFacts()
    }

    suspend fun fetchAndStoreFact(): Result<FunFact> {
        return try {
            val fact = ktorClient.get("https://uselessfacts.jsph.pl/api/v2/facts/random?language=en")
                .body<FunFact>()

            funFactDao.insertFact(fact)

            Result.success(fact)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}