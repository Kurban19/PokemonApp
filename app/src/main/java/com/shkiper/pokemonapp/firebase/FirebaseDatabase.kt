package com.shkiper.pokemonapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.shkiper.pokemonapp.model.Pokemon
import com.shkiper.pokemonapp.model.Resource
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

    fun initCurrentUserIfFirstTime() {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                with(FirebaseAuth.getInstance().currentUser){
                    val newUser = User(this!!.uid, email = email ?: "")
                    currentUserDocRef.set(newUser)
                }
            }
        }
    }

    fun addPokemonToFavorites(pokemon: Pokemon){
        favoritesCollectionRef
                .document(currentUserDocRef.id)
                .collection("listOfFavorites")
                .document(pokemon.id)
                .set(pokemon)
    }

    fun deleteFromFavorites(pokemonId: String){
        favoritesCollectionRef
                .document(currentUserDocRef.id)
                .collection("listOfFavorites")
                .document(pokemonId)
                .delete()
    }

    fun getFavorites(): MutableList<Pokemon> {
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


    fun addFavoritesListener(
            onListen: (List<Pokemon>) -> Unit
    ): ListenerRegistration {
        return favoritesCollectionRef.document(currentUserDocRef.id)
                .collection("listOfFavorites")
                .addSnapshotListener { querySnapshot, firebaseFireStoreException ->
                    if (firebaseFireStoreException != null) {
                        return@addSnapshotListener
                    }

                    val items = mutableListOf<Pokemon>()
                    querySnapshot?.documents?.forEach {
                        val message = it.toObject(Pokemon::class.java)
                        if (message != null) {
                            items.add(message)
                        }
                        return@forEach
                    }
                    onListen(items)
                }
    }

}