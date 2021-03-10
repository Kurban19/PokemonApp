package com.shkiper.pokemonapp.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var pokemon: Pokemon
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        search_button.setOnClickListener {
            val query = search_edit_text.text.toString().trim()
            if (query.isBlank()) {
                Snackbar.make(view, getString(R.string.search_alert), Snackbar.LENGTH_LONG).show()
            } else {
                findPokemon(query.toLowerCase())
            }
        }

        iv_add_to_favorites.setOnClickListener {
            viewModel.addToFavorites(pokemon)
            iv_add_to_favorites.visibility = View.GONE
        }

    }

    private fun findPokemon(query: String) {
        viewModel.refresh(query)
        viewModel.pokemon.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS ->{
                    hideLoader()
                    showPokemon(it.data!!)
                    pokemon = it.data
                }
                Resource.Status.LOADING ->{
                    showLoader()
                }
                Resource.Status.ERROR ->{
                    hideLoader()
                    Log.d("tAg", it.message.toString())
                    showError()
                }
            }
        })
    }


    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    private fun showPokemon(pokemon: Pokemon) {
        iv_add_to_favorites.visibility = View.VISIBLE
        pokemon_result.visibility = View.VISIBLE
        Glide.with(iv_pokemon_image)
                .load(pokemon.getImageUrl())
                .into(iv_pokemon_image)

        tv_pokemon_name.text = pokemon.name
        tv_base_experience_scores.text = pokemon.experience.toString()
        tv_weight_scores.text = pokemon.weight
        tv_height_scores.text = pokemon.height

    }

    private fun showLoader() {
        no_results_placeholder.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        progress_bar.visibility = View.GONE
    }

    private fun showError() {
        pokemon_result.visibility = View.GONE
        no_results_placeholder.visibility = View.VISIBLE
        no_results_placeholder.text = no_results_placeholder.context.getString(R.string.error)
    }

}