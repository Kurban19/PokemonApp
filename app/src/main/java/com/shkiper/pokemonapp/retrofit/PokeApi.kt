package com.shkiper.pokemonapp.retrofit

import kotlin.random.Random

interface PokeApi {

    suspend fun searchPokemon(name: String)

    suspend fun getRandomPokemon()

}