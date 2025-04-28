package com.example.wishlistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Main Room Database class for the app.
 * This class is an abstract representation of the SQLite database.
 * It tells Room what entities (tables) to use and provides access to the DAO(s).
 */
@Database(
    entities = [Wish::class],      // This database holds a table for the Wish entity
    version = 1,                  // Database version (used for migrations when schema changes)
    exportSchema = false          // Schema export disabled (used for version control in large projects)
)
abstract class WishDatabase : RoomDatabase() {
    /**
     * Abstract function that returns the DAO (Data Access Object) for Wish.
     * Room will automatically generate the implementation.
     * This is how the rest of the app will access database operations.
     */
    abstract fun wishDao(): WishDAO
}