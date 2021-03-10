package com.shkiper.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shkiper.pokemonapp.firebase.FirebaseDatabase
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.retrofit.PokeApi
import kotlinx.coroutines.launch

class RandomViewModel(private val apiHelper: PokeApi): ViewModel() {


    private val pokemon = MutableLiveData<Resource<Pokemon>>()


    fun fetchRandomPokemon(){
        viewModelScope.launch {
            pokemon.postValue(Resource.loading(null))
            try {
//                pokemon.postValue(Resource.success(apiHelper.getRandomPokemon()))
            } catch (e: Exception) {
                pokemon.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun addToFavorite(pokemon: Pokemon){
        FirebaseDatabase.addPokemonToFavorites(pokemon)
    }

    fun getPokemon(): LiveData<Resource<Pokemon>>? {
        return pokemon
    }

}