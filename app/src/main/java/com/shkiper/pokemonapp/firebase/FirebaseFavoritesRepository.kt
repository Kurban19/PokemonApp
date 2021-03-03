package com.shkiper.pokemonapp.firebase

interface FirebaseFavoritesRepository {

    fun getFavorites()

    fun addToFavorites(id: String)

    fun deleteFromFavorites(id: String)

}