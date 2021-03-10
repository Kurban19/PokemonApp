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

    constructor(application: Application, test: Boolean = true) : this(application) {
        injected = true
    }

    val pokemon by lazy {MutableLiveData<Resource<Pokemon>>()}


    private val disposable: CompositeDisposable = CompositeDisposable()
    private var injected = false

    @Inject
    lateinit var apiService: PokeApiService


    private fun fetchRandomPokemon(){
        pokemon.postValue(Resource.loading(null))
        disposable.add(
            apiService.getRandomPokemon()
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
        if (!injected) {
            DaggerViewModelComponent.builder()
                .appModule(
                    AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }


    fun refresh() {
        inject()
        fetchRandomPokemon()
    }

    fun addToFavorite(pokemon: Pokemon){
        FirebaseDatabase.addPokemonToFavorites(pokemon)
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}