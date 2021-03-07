package com.shkiper.pokemonapp.ui.fragments.random

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.retrofit.PokeApiImpl
import com.shkiper.pokemonapp.retrofit.RetrofitBuilder
import com.shkiper.pokemonapp.utill.ViewModelFactory
import com.shkiper.pokemonapp.viewmodel.RandomViewModel
import kotlinx.android.synthetic.main.fragment_random.*
import kotlinx.android.synthetic.main.fragment_search.*

class RandomFragment : Fragment() {

    private lateinit var viewModel: RandomViewModel
    private lateinit var pokemon: Pokemon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()


        btn_get_random.setOnClickListener {
            getPokemon()
        }


        iv_add_to_favorites_random.setOnClickListener {
            viewModel.addToFavorite(pokemon)
            iv_add_to_favorites_random.visibility = View.GONE
        }



    }


    private fun getPokemon() {
        showLoader()
        viewModel.fetchRandomPokemon()
        viewModel.getPokemon()?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    hideLoader()
                    showPokemon(it.data!!)
                    pokemon = it.data
                }
                Resource.Status.LOADING -> {
                    showLoader()
                }
                Resource.Status.ERROR -> {
                    hideLoader()
                    Log.d("tAg", it.message.toString())
                    showError()
                }
            }
        })
    }


    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(PokeApiImpl(RetrofitBuilder.apiService)))
                .get(
                    RandomViewModel::class.java
                )
    }

    private fun showPokemon(pokemon: Pokemon) {
        iv_add_to_favorites_random.visibility = View.VISIBLE
        pokemon_result_random.visibility = View.VISIBLE
        Glide.with(iv_pokemon_image_random)
            .load(pokemon.getImageUrl())
            .into(iv_pokemon_image_random)

        tv_base_experience_scores_random.text = pokemon.experience.toString()
        tv_pokemon_name_random.text = pokemon.name
    }

    private fun showLoader() {
        no_results_placeholder_random.visibility = View.GONE
        progress_bar_random.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        progress_bar_random.visibility = View.GONE
    }

    private fun showError() {
        pokemon_result_random.visibility = View.GONE
        no_results_placeholder_random.visibility = View.VISIBLE
        no_results_placeholder_random.text = no_results_placeholder.context.getString(R.string.error)
    }
}