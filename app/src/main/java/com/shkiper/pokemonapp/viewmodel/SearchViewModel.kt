package com.shkiper.pokemonapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shkiper.pokemonapp.firebase.FirebaseDatabase
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.retrofit.PokeApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

class SearchViewModel(private val apiHelper: PokeApi): ViewModel() {


    private val pokemon = MutableLiveData<Resource<Pokemon>>()


    fun findPokemon(query: String){
        viewModelScope.launch {
            pokemon.postValue(Resource.loading(null))
            try {
                pokemon.postValue(Resource.success(apiHelper.searchPokemon(name = query)))
            } catch (e: Exception) {
                pokemon.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun addToFavorites(pokemon: Pokemon){
        FirebaseDatabase.addPokemonToFavorites(pokemon)
    }

    fun getPokemon(): LiveData<Resource<Pokemon>> {
        return pokemon
    }

}