package com.shkiper.pokemonapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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


        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment? ?: return
        val navController = host.navController
        setUpBottomNav(navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.search_page->navController.navigate(R.id.FirstFragment)
                R.id.random_page->navController.navigate(R.id.SecondFragment)
                R.id.favorites_page->navController.navigate(R.id.ThirdFragment)
            }
            true
        }
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
    }

}