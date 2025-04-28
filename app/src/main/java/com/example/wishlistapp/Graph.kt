package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository

/**
 * A Singleton object called 'Graph' that acts as a simple Service Locator / DI container.
 * It's responsible for initializing and providing the Room database and the repository.
 */
object Graph {

    // Lateinit variable to hold the Room Database instance
    // It will be initialized in the 'provide()' method
    lateinit var database: WishDatabase

    /**
     * Lazy initialization of the repository.
     * 'wishRepository' is created only when accessed for the first time.
     * This avoids redundant repository creation and uses the already-built database.
     */
    val wishRepository by lazy {
        WishRepository(wishDAO = database.wishDao())
    }

    /**
     * This function is called at the start of the app (usually from Application class or MainActivity).
     * It builds the Room Database instance using the application context.
     */
    fun provide(context: Context) {
        database = Room.databaseBuilder(
            context,
            WishDatabase::class.java,
            "wishlist.db" // The name of the SQLite database file
        ).build()
    }
}