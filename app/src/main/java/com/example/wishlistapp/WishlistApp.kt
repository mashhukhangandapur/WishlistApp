package com.example.wishlistapp.ui.theme

import android.app.Application
import com.example.wishlistapp.Graph

// Custom Application class for the Wishlist App
class WishlistApp : Application() {

    // This method is called when the application is starting, before any activity, service, or receiver objects have been created.
    override fun onCreate() {
        super.onCreate()

        // Initialize the Graph object with the application context.
        // This sets up the Room database and the repository for the app.
        Graph.provide(this)
    }
}
