package com.shkiper.pokemonapp.retrofit

import com.shkiper.pokemonapp.di.component.DaggerApiComponent
import com.shkiper.pokemonapp.model.Pokemon
import io.reactivex.Single
import javax.inject.Inject

class PokeApiService{

    @Inject
    lateinit var apiService: PokeApi

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    fun searchPokemon(name: String): Single<Pokemon> = apiService.searchPokemon(name)

    fun getRandomPokemon(): Single<Pokemon> = apiService.getRandomPokemon()
}