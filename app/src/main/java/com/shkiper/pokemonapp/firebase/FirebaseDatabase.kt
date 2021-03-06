package com.shkiper.pokemonapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.User

object FirebaseDatabase {

    private val fireStoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }


    private val favoritesCollectionRef = fireStoreInstance.collection("favorites")

    private val currentUserDocRef: DocumentReference
        get() = fireStoreInstance.document(
            "users/${
                FirebaseAuth.getInstance().currentUser?.uid
                    ?: throw NullPointerException("UID is null.")}"
        )

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                with(FirebaseAuth.getInstance().currentUser){
                    val newUser = User(this!!.uid, email = email ?: "")
                    currentUserDocRef.set(newUser)
                }
            }
        }.addOnCompleteListener { onComplete }
    }




    fun addPokemonToFavorites(pokemon: Pokemon){
        favoritesCollectionRef
                .document(currentUserDocRef.id)
                .collection("listOfFavorites")
                .document(pokemon.id)
                .set(pokemon)
    }

    fun getFavorites(): List<Pokemon>{
        val listOfPokemon = mutableListOf<Pokemon>()
        favoritesCollectionRef.document(currentUserDocRef.id)
                .collection("listOfFavorites")
                .get().addOnSuccessListener{ result ->
                    for(document in result){
                        val pokemon = document.toObject(Pokemon::class.java)
                        listOfPokemon.add(pokemon)
                    }
                }
        return listOfPokemon
    }


}