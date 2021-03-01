package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.model.Pokemon
import retrofit2.Call

interface PokeApi {

    suspend fun searchPokemon(name: String): Pokemon

    suspend fun getRandomPokemon(): Pokemon

}