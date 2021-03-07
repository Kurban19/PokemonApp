package com.shkiper.pokemonapp.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
import com.shkiper.pokemonapp.ui.adapter.PokemonAdapter
import com.shkiper.pokemonapp.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_random.*
import kotlinx.android.synthetic.main.fragment_search.*


class FavoritesFragment : Fragment() {


    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModel()
        setupObserver()
    }


    private fun initViews(){
        pokemonAdapter = PokemonAdapter()


        with(rv_result){
            adapter = pokemonAdapter
            layoutManager = LinearLayoutManager(this@FavoritesFragment.context)
        }

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
    }

    private fun setupObserver() {
        viewModel.getFavorites().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val pokemons = it.data ?: emptyList()
                    if(pokemons.isNotEmpty()){
                        showResult(pokemons)
                    }
                    else
                        showEmpty()
                }
                Resource.Status.LOADING -> {
                    showLoader()
                }
                Resource.Status.ERROR -> {
                    hideLoader()
                    showError()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setupObserver()
    }

    private fun showResult(pokemons: List<Pokemon>) {
        rv_result.visibility = View.VISIBLE
        pokemonAdapter.updateData(pokemons)
    }


    private fun showLoader() {
        no_results_placeholder_favorites.visibility = View.GONE
        progress_bar_favorites.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        no_results_placeholder_favorites.visibility = View.GONE
        progress_bar_favorites.visibility = View.GONE
    }


    private fun showEmpty() {
        rv_result.visibility = View.GONE
        no_results_placeholder_favorites.visibility = View.VISIBLE
        no_results_placeholder_favorites.text = no_results_placeholder_favorites.context.getString(R.string.empty_result)
    }

    private fun showError() {
        rv_result.visibility = View.GONE
        no_results_placeholder_favorites.visibility = View.VISIBLE
        no_results_placeholder_favorites.text = no_results_placeholder_favorites.context.getString(R.string.error)
    }

}