package com.example.wishlistapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AppBarView(
    title : String,
    onBackNavClicked :() -> Unit ={} // Optional lambda for back navigation click
) {
    // Decide whether to show the back button or not
    val navigationIcon: (@Composable () -> Unit)? = {
        if (!title.contains("WishList")) { // Only show back icon if not on home screen
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    else {
            null  // No icon for Home screen
        }
    }

    TopAppBar(
        title = {
                Text(
                    text = title,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
        },
        elevation = 3.dp,
        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
        backgroundColor = colorResource(id = R.color.app_bar),
        navigationIcon = navigationIcon //Show icon if needed
    )
}

