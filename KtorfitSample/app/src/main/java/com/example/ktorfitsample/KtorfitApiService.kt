package com.example.ktorfitsample

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface KtorfitApiService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
    ): PokemonsResponse
}