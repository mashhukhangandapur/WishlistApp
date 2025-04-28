package com.example.wishlistapp

// UI and basic functionality imports
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(navController: NavController,
             viewModel: WishViewModel){

    val context = LocalContext.current // To show Toast messages

    // Main screen layout using Scaffold
    Scaffold(
        // Top app bar at the top of screen
        topBar = {AppBarView(title = "WishList", {
            navController.popBackStack()
            Toast.makeText(context,"Back Button Clicked.", Toast.LENGTH_LONG).show()
        })},
        // Floating button on bottom right
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 15.dp),
                contentColor = Color.White,
                backgroundColor = Color.Black,
                onClick = {
                    Toast.makeText(context,"Add Button Clicked.",Toast.LENGTH_LONG).show()
                    // Navigate to AddScreen with id 0L (for new item)
                    navController.navigate(Screen.AddScreen.route + "/0L")
                },
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        // Content of scaffold (everything below TopBar)
        // Get all wishes from ViewModel (observe flow)
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        // Scrollable vertical list of wishes
        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {

            // Display each wish one by one
            items(wishlist.value, key = {wish -> wish.id}) { wish ->
                // Swipe-to-delete logic
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        // If user swiped fully, delete the item
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            viewModel.deleteWish(wish) // Delete from DB
                        }
                        true // Confirm state change
                    }
                )
                // Wrapper to detect swipe and show delete icon
                SwipeToDismiss(
                    state = dismissState, // Tracks swipe state
                    background = {
                        // Animate background color when swiping
                        val color by animateColorAsState(
                            if(dismissState.dismissDirection
                                == DismissDirection.EndToStart) Color.Red else Color.Transparent,
                            label = ""
                        )
                        val alignment = Alignment.CenterEnd // Align delete icon to end
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp)
                                .background(color),
                            contentAlignment = alignment){
                            Icon(Icons.Default.Delete,
                                contentDescription = "Delete icon",
                                tint = Color.White)
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart), // Allow swipe left only
                    dismissThresholds = {FractionalThreshold(0.75f)},  // Swipe 75% to trigger
                    dismissContent = {
                        // Content of card (shown when not swiped)
                        WishItem(wish = wish) {
                            val id = wish.id // Get ID of this wish
                            // Navigate to AddScreen with wish ID to edit
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    }
                )
            }
        }
    }
}
// Reusable UI for each wish card
@Composable
fun WishItem(wish : Wish, onClick :() -> Unit){
    Card(modifier = Modifier.fillMaxWidth().
        padding(top = 8.dp, start = 8.dp, end = 8.dp).
        clickable{
            onClick()
    }, elevation = 10.dp,
        backgroundColor= Color.White

        )
    {
        Column(modifier = Modifier.padding(16.dp),
        ) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}