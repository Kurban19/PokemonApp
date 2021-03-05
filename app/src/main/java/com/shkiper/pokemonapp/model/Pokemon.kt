package com.shkiper.pokemonapp.model

import com.google.gson.annotations.SerializedName

data class Pokemon(val id: String,
                   val name: String,
                   @SerializedName("base_experience")
                   val experience: Int,
                   val height: String,
                   val weight: String){

    fun getImageUrl(): String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }
}