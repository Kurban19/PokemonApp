package com.shkiper.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
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
            FirebaseDatabase.addFavoritesListener (this::setPokemons)
        } catch (e: Exception) {
            favorites.postValue(Resource.error(e.toString(), null))
        }
    }

    fun setPokemons(pokemons: List<Pokemon>){
        favorites.postValue(Resource.success(pokemons))
    }


    fun getFavorites(): LiveData<Resource<List<Pokemon>>> {
        return favorites
    }

}