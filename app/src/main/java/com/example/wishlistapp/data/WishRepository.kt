package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

/**
 * WishRepository acts as a middle layer between ViewModel and the DAO (WishDAO).
 * It hides the actual data source and provides a clean API for accessing wish data.
 * All database operations are performed through this class.
 */

class WishRepository(private val wishDAO: WishDAO) {

    /**
     * Adds a new wish to the database.
     * This is a suspend function, so it should be called from a coroutine.
     */
    suspend fun addWish(wish: Wish){
        wishDAO.addWish(wish)
    }
    /**
     * Retrieves all wishes as a Flow.
     * Flow allows real-time observation; UI will update whenever the data changes.
     */
    suspend fun getAllWishes() : Flow<List<Wish>> = wishDAO.getAllWishes()

    /**
     * Retrieves a single wish by its ID.
     * Returns a Flow so it can be observed reactively (live updates).
     */
    fun getAWishById(id: Long) : Flow<Wish>{
        return wishDAO.getAWishById(id)
    }

    /**
     * Updates an existing wish in the database.
     */
    suspend fun updateWish(wish: Wish){
        wishDAO.updateWish(wish)
    }
    /**
     * Updates an existing wish in the database.
     */
    suspend fun deleteWish(wish: Wish){
        wishDAO.deleteAWish(wish)
    }

    /*

     🔍 In Short:
        This class doesn’t directly deal with UI or Room — it just passes requests to WishDAO.

        The idea is to separate concerns:
        UI 🔁 ViewModel 🔁 Repository 🔁 DAO 🔁 Database

        It’s also future-proof — if you change the data source later (like use network instead of DB), your UI won’t break.
     */


}