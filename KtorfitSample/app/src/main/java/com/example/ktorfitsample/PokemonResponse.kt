package com.example.ktorfitsample

import kotlinx.serialization.Serializable

@Serializable
data class PokemonsResponse(
    val count: Int,
    val next: String?,
    val previous: Int?,
    val results: List<Result>?
)

@Serializable
data class Result(
    val name: String,
    val url: String,
)
