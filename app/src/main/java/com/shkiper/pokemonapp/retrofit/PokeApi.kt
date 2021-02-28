package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.model.Pokemon
import retrofit2.Call
import kotlin.random.Random

interface PokeApi {

    suspend fun searchPokemon(name: String): Call<List<Pokemon>>

    suspend fun getRandomPokemon(): Call<List<Pokemon>>

}