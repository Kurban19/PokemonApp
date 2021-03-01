package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.random.Random

interface PokeApiService {

    @GET("pokemon/{pokemon_name}")
    suspend fun searchPokemon(
        @Path("pokemon_name") name: String
    ): Pokemon

    @GET("pokemon/{pokemon_id}")
    suspend fun getRandomPokemon(
        @Path("pokemon_id") id: String = Random.nextInt(0, 900).toString()
    ): Pokemon
}