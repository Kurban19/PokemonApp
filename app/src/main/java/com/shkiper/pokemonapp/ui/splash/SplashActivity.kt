package com.shkiper.pokemonapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))

//        if(FirebaseAuth.getInstance().currentUser == null){
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//        else{
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//        finish()
    }

}