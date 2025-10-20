package com.mastercyber.tp1

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.Pokemon
import kotlin.collections.get

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = "Hello, ${platform.name}!"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetchPokemon(): Pokemon {
        val random = (1..1025).random()
        return client.get("https://tyradex.vercel.app/api/v1/pokemon/$random").body()
    }

    fun guessPokemon(name: String, guess: String): Boolean {
        return name.lowercase() == guess.lowercase()
    }
}