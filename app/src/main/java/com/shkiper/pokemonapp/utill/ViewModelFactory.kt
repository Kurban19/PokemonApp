package com.shkiper.pokemonapp.utill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shkiper.pokemonapp.retrofit.PokeApi
import com.shkiper.pokemonapp.viewmodel.SearchViewModel

class ViewModelFactory(private val apiHelper: PokeApi) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}