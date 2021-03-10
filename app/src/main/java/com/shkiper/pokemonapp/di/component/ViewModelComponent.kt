package com.shkiper.pokemonapp.di.component

import com.shkiper.pokemonapp.di.module.ApiModule
import com.shkiper.pokemonapp.viewmodel.RandomViewModel
import com.shkiper.pokemonapp.viewmodel.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: SearchViewModel)

    fun inject(viewModel: RandomViewModel)
}