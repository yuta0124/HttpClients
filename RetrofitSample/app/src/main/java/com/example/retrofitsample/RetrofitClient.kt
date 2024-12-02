package com.example.retrofitsample

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://pokeapi.co/api/v2/"
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            provideJson().asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(BASE_URL)
        .client(provideHttpClient())
        .build()
        .create(RetrofitApiService::class.java)

    private fun provideHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor { Log.v("API", it) }
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
        )
        .build()

    private fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    suspend fun getPokemons(limit: Int?, offset: Int?): PokemonsResponse? =
        runCatching {
            retrofit.getPokemons(limit, offset)
        }.fold(
            onSuccess = { it },
            onFailure = { null },
        )
}