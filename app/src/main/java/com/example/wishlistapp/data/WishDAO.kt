package com.example.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/*
    * DAO (Data Access Object) interface for Room Database.
    * This interface defines all the operations (queries) related to the `Wish` entity.
    * Room will automatically generate the implementation for this abstract class.
*/

@Dao
abstract class WishDAO {

    /**
     * Inserts a new wish into the database.
     * If the wish already exists (conflict), it will ignore the new one.
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addWish(wishEntity : Wish)

    /**
     * Retrieves all wishes from the database as a Flow.
     * Using Flow allows us to observe the data and get real-time updates.
     */
    @Query("Select * from `wish-table`")
    abstract fun getAllWishes() : Flow<List<Wish>>

    /**
     * Updates an existing wish in the database.
     * The wish must already exist, otherwise nothing happens.
     */

    @Update
    abstract fun updateWish(wishEntity: Wish)

    /**
     * Deletes a specific wish from the database.
     */
    @Delete
    abstract fun deleteAWish(wishEntity: Wish)

    /**
     * Fetches a single wish by its ID.
     * Returns a Flow so the data can be observed in real-time.
     */
    @Query("Select * from `wish-table`  where id =:id")
    abstract fun getAWishById(id : Long) : Flow<Wish>
}