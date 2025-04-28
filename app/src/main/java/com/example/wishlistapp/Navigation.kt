package com.example.wishlistapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController = rememberNavController(),
               viewModel : WishViewModel = viewModel()){
    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route)
    {
        composable(route = Screen.HomeScreen.route) {
            HomeView(navController,viewModel)
        }
        /**
         * What's happening inside 2nd composable ?
         * Basically, we are providing data to the composable where we will navigate to...
         * We are saying, go to Add screen and inside add screen, navigate to the wish,
         * whose id is this ! and other arguments who is specifying or making things clear,
         * are type of id , default value , whether the id can be null or not ?
         */
        composable(route = Screen.AddScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )) { entry ->    // Below the arguments are unpacked,
            val id = if(entry.arguments != null)  entry.arguments!!.getLong("id") else 0L

            AddEditDetailView(id = id, navController= navController, viewModel = viewModel)
        }
    }
}