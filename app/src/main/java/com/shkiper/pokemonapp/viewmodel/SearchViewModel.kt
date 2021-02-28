package com.shkiper.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.retrofit.PokeApi
import kotlinx.coroutines.launch


class SearchViewModel(private val apiHelper: PokeApi): ViewModel() {


    private val movies = MutableLiveData<Resource<List<Pokemon>>>()


    fun findMovies(query: String){
        viewModelScope.launch {
            movies.postValue(Resource.loading(null))
            try {
                val resultFromApi = apiHelper.searchPokemon(name = query)
//                movies.postValue(Resource.success(resultFromApi))
            } catch (e: Exception) {
                movies.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getMovies(): LiveData<Resource<List<Pokemon>>>? {
        return movies
    }

}