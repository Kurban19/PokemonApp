package com.shkiper.pokemonapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.ui.fragments.favorites.FavoritesFragment
import com.shkiper.pokemonapp.ui.fragments.random.RandomFragment
import com.shkiper.pokemonapp.ui.fragments.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PokemonApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchFragment = SearchFragment()
        val randomFragment = RandomFragment()
        val favoritesFragment = FavoritesFragment()

        setCurrentFragment(searchFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.search_page->setCurrentFragment(searchFragment)
                R.id.random_page->setCurrentFragment(randomFragment)
                R.id.favorites_page->setCurrentFragment(favoritesFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}