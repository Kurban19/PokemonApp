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

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    val pokemon by lazy { MutableLiveData<Resource<Pokemon>>() }

    private val disposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var apiService: PokeApiService

    init {
        inject()
    }



    private fun findPokemon(query: String){
        pokemon.postValue(Resource.loading(null))
        disposable.add(
            apiService.searchPokemon(query)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Pokemon>() {
                override fun onSuccess(pokemonlList: Pokemon) {
                    pokemon.value = Resource.success(pokemonlList)
                }
                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    pokemon.value = Resource.error(e.printStackTrace().toString(),null)
                }
            })
        )

    }

    private fun inject() {
        DaggerViewModelComponent.builder()
                .appModule(
                        AppModule(getApplication())
                )
                .build()
                .inject(this)
    }


    fun refresh(searchQuery: String) {
        findPokemon(searchQuery)
    }


    fun addToFavorites(pokemon: Pokemon){
        FirebaseDatabase.addPokemonToFavorites(pokemon)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
