package com.shkiper.pokemonapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.ui.fragments.favorites.FavoritesFragment
import com.shkiper.pokemonapp.ui.fragments.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = SearchFragment()
        val secondFragment = FavoritesFragment()

        setCurrentFragment(firstFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.search_page->setCurrentFragment(firstFragment)
                R.id.favorites_page->setCurrentFragment(secondFragment)
//                R.id.settings->setCurrentFragment(thirdFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.navigationHostFragment,fragment)
            commit()
        }
}