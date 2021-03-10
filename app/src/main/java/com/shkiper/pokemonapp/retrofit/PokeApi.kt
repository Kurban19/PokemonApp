package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.model.Pokemon
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.random.Random

interface PokeApi {

    @GET("pokemon/{pokemon_name}")
    fun searchPokemon(
        @Path("pokemon_name") name: String
    ): Single<Pokemon>

    @GET("pokemon/{pokemon_id}")
    fun getRandomPokemon(
        @Path("pokemon_id") id: String = Random.nextInt(0, 900).toString()
    ): Single<Pokemon>
}