package com.shkiper.pokemonapp.retrofit

import kotlin.random.Random

class PokeApiImpl(private val apiService: PokeApiService): PokeApi {

    override suspend fun searchPokemon(name: String) = apiService.searchPokemon(name)

    override suspend fun getRandomPokemon() = apiService.getRandomPokemon()


}