package com.shkiper.pokemonapp.firebase

interface FirebaseDatabase {

    fun getFavorites()

    fun addToFavorites(id: String)

    fun deleteFromFavorites(id: String)

}