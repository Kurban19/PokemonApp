package com.shkiper.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                val resultFromApi = apiHelper.searchPokemon(name = query)
                resultFromApi.enqueue(object :Callback<Pokemon>{
                    override fun onResponse(
                        call: Call<Pokemon>,
                        response: Response<Pokemon>
                    ) {
                        pokemon.postValue(Resource.success(response.body()))
                    }

                    override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                        pokemon.postValue(Resource.error(t.localizedMessage.toString(), null))
                    }

                })
            } catch (e: Exception) {
                pokemon.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getPokemon(): LiveData<Resource<Pokemon>>? {
        return pokemon
    }

}