package com.shkiper.pokemonapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shkiper.pokemonapp.firebase.FirebaseDatabase
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource

class FavoritesViewModel : ViewModel(){

    private val favorites = MutableLiveData<Resource<List<Pokemon>>>()

    init {
        fetchFavorites()
    }

    private fun fetchFavorites(){
        favorites.postValue(Resource.loading(null))
        try {
            favorites.postValue(Resource.success(FirebaseDatabase.getFavorites()))
        } catch (e: Exception) {
            favorites.postValue(Resource.error(e.toString(), null))
        }
    }


    fun getFavorites(): MutableLiveData<Resource<List<Pokemon>>> {
        return favorites
    }

}