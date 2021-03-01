package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.model.Pokemon
import kotlin.random.Random

class PokeApiImpl(private val apiService: PokeApiService): PokeApi {

    override suspend fun searchPokemon(name: String): Pokemon = apiService.searchPokemon(name)

    override suspend fun getRandomPokemon(): Pokemon = apiService.getRandomPokemon()
}