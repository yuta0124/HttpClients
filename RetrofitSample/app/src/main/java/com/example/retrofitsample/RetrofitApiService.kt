package com.example.retrofitsample

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
    ): PokemonsResponse
}