package com.shkiper.pokemonapp.retrofit


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"


    private fun getRetrofit(): Retrofit{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: PokeApi = getRetrofit().create(PokeApi::class.java)

}