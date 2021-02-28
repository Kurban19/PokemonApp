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


    private val movies = MutableLiveData<Resource<List<Pokemon>>>()


    fun findMovies(query: String){
        viewModelScope.launch {
            movies.postValue(Resource.loading(null))
            try {
                val resultFromApi = apiHelper.searchPokemon(name = query)
//                movies.postValue(Resource.success(resultFromApi))
                resultFromApi.enqueue(object :Callback<List<Pokemon>>{
                    override fun onResponse(
                        call: Call<List<Pokemon>>,
                        response: Response<List<Pokemon>>
                    ) {
                        movies.postValue(Resource.success(response.body()))
                    }

                    override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                        movies.postValue(Resource.error(t.localizedMessage.toString(), null))
                    }

                })
            } catch (e: Exception) {
                movies.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getMovies(): LiveData<Resource<List<Pokemon>>>? {
        return movies
    }

}