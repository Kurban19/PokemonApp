package com.shkiper.pokemonapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.shkiper.pokemonapp.di.component.DaggerViewModelComponent
import com.shkiper.pokemonapp.di.module.AppModule
import com.shkiper.pokemonapp.firebase.FirebaseDatabase
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.retrofit.PokeApi
import com.shkiper.pokemonapp.retrofit.PokeApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomViewModel(application: Application) : AndroidViewModel(application) {


    val pokemon by lazy {MutableLiveData<Resource<Pokemon>>()}


    @Inject
    lateinit var apiService: PokeApiService

    init{
        inject()
    }

    private fun fetchRandomPokemon(){

        viewModelScope.launch {
            pokemon.postValue(Resource.loading(null))
            try {
                pokemon.postValue(Resource.success(apiService.getRandomPokemon()))
            } catch (e: Exception) {
                pokemon.postValue(Resource.error(e.toString(), null))
            }
        }
    }


    private fun inject() {
        DaggerViewModelComponent.builder()
                .appModule(
                        AppModule(getApplication()))
                .build()
                .inject(this)
    }


    fun refresh() {
        fetchRandomPokemon()
    }

    fun addToFavorite(pokemon: Pokemon){
        FirebaseDatabase.addPokemonToFavorites(pokemon)
    }


}