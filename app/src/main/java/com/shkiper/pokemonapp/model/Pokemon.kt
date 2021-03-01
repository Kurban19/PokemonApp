package com.shkiper.pokemonapp.model

import com.google.gson.annotations.SerializedName

data class Pokemon(val id: String,
                   val name: String,
                   val height: String,
                   val weight: String,
                   val description: String,
                    @SerializedName("sprites/other/official-artwork/front_default")
                   val imageUrl: String)