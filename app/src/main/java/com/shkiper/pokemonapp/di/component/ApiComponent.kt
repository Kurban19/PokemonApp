package com.shkiper.pokemonapp.di.component

import com.shkiper.pokemonapp.di.module.ApiModule
import com.shkiper.pokemonapp.retrofit.PokeApi
import com.shkiper.pokemonapp.retrofit.PokeApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: PokeApiService)
}