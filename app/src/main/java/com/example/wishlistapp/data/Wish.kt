package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title : String = "",
    @ColumnInfo(name = "wish-desc")
    val description : String = ""
)

//object DummyWish{
//              val  wishList =  listOf(
//                  Wish(title = "A Good Practicing Muslim",
//                      description = "To please The Almighty and follow Sirat E Mustakeem."),
//                  Wish(title = "Achieve Taqwa ",
//                      description = "By doing everything that kills nafs."),
//                  Wish(title = "Patience in every situation",
//                      description = "The most beautiful thing.")
//              )
//}