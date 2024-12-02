package com.example.ktorfitsample

import android.util.Log
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor

private const val BASE_URL = "https://pokeapi.co/api/v2/"

object KtorfitClient {
    private val ktorfit = Ktorfit
        .Builder()
        .baseUrl(BASE_URL)
        .httpClient(provideHttpClient())
        .build()
        .createKtorfitApiService()

    private fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
        engine {
            addInterceptor(
                HttpLoggingInterceptor { Log.v("API", it) }
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
            )
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }

        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun getPokemons(limit: Int?, offset: Int?): PokemonsResponse? =
        runCatching {
            ktorfit.getPokemons(limit, offset)
        }.fold(
            onSuccess = { it },
            onFailure = { null },
        )
}