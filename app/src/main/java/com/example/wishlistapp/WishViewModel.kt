package com.example.wishlistapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing UI state and handling interaction logic for wishes.
 * This sits between the UI (Jetpack Compose) and the data layer (Room via Repository).
 */
class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository // Repository instance injected via constructor
) : ViewModel() {

    // State variables to hold input field values for title and description
    var wishTitleState by mutableStateOf("")          // Title input state
    var wishDescriptionState by mutableStateOf("")    // Description input state

    // Called when title text field value changes
    fun onWishTitleChanged(newString: String) {
        wishTitleState = newString
    }

    // Called when description text field value changes
    fun onWishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

    // Will hold the list of all wishes as a Flow (async data stream from Room)
    lateinit var getAllWishes: Flow<List<Wish>>

    // Initial block that fetches all wishes when ViewModel is first created
    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getAllWishes()
        }
    }

    /**
     * Adds a new wish to the database using the repository.
     * Runs on IO thread to avoid blocking the main thread.
     */
    fun addAWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish = wish)
        }
    }

    /**
     * Updates an existing wish in the database.
     * Uses default dispatcher (Main thread) since Room handles threading.
     */
    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish = wish)
        }
    }

    /**
     * Deletes a wish from the database.
     */
    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish = wish)
        }
    }

    /**
     * Retrieves a single wish by its ID.
     */
    fun getAWishById(id: Long) : Flow<Wish> {
        return wishRepository.getAWishById(id)
    }

}
