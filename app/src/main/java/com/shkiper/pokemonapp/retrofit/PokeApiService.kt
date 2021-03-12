package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.di.component.DaggerApiComponent
import com.shkiper.pokemonapp.model.Pokemon
import io.reactivex.Single
import javax.inject.Inject

class PokeApiService{

    @Inject
    lateinit var api: PokeApi

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    fun searchPokemon(name: String): Single<Pokemon> = api.searchPokemon(name)

    suspend fun getRandomPokemon(): Pokemon = api.getRandomPokemon()
}