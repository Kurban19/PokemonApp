package com.shkiper.pokemonapp.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.retrofit.PokeApiImpl
import com.shkiper.pokemonapp.retrofit.RetrofitBuilder
import com.shkiper.pokemonapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.utill.ViewModelFactory

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                findPokemon(query)
            }
        }
    }

    private fun findPokemon(query: String) {
        showLoader()
        viewModel.findPokemon(query)
        viewModel.getPokemon()?.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS ->{
                    hideLoader()
                    showPokemon(it.data!!)
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
        viewModel =  ViewModelProviders.of(this, ViewModelFactory(PokeApiImpl(RetrofitBuilder.apiService))).get(
                SearchViewModel::class.java)
    }


    private fun showPokemon(pokemon: Pokemon) {
        Glide.with(iv_pokemon_image)
                .load(pokemon.imageUrl)
                .into(iv_pokemon_image)

        tv_pokemon_name.text = pokemon.name
    }

    private fun showLoader() {
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