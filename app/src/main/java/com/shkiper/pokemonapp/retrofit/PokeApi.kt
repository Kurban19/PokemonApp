package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.model.Pokemon

interface PokeApi {

    suspend fun searchPokemon(name: String): Pokemon

    suspend fun getRandomPokemon(): Pokemon

}