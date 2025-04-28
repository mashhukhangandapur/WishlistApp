package com.example.wishlistapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember{
        mutableStateOf("")
    }
    // Remember a CoroutineScope to show the Snackbar
    val scope = rememberCoroutineScope()
    // Create a snackbarHostState to manage the Snackbar's state
    val snackbarHostState = remember { SnackbarHostState() }   // Scaffold state is taking care of everything inside
    // the scaffold UI

    if(id != 0L){
        val wish= viewModel.getAWishById(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        // Provide the SnackBarHost to the Scaffold
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { AppBarView (
                title = if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish)
            ) { navController.navigateUp() } } )
    {
        Column(
            modifier = Modifier
                .padding(it).padding(start = 6.dp, end =6.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Example text field usage
            WishTextField(label = "Title",
                value = viewModel.wishTitleState,
                onValueChanged = {
                    viewModel.onWishTitleChanged(it)
                })

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onWishDescriptionChanged(it)
                })

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty()
                    && viewModel.wishDescriptionState.isNotEmpty()){
                    if(id != 0L){
                        //Updating wish
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()
                             )
                        )
                    }
                    else{
                        //Adding Wish
                        viewModel.addAWish(
                            Wish(title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim())
                        )
                        snackMessage.value = "Wish has been created"
                    }
                }
                else{
                    // Add a wish before pressing add wish button
                    snackMessage.value = "Enter fields to create a wish."
                }
                // Launch a coroutine to show the Snackbar
                scope.launch {
                    snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.app_bar))
            ) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish)
                else
                    stringResource(id = R.string.add_wish),

                    style = TextStyle(fontSize = 18.sp)
                    )

            }

            Spacer(modifier = Modifier.height(10.dp))

            // Add more fields or a button as needed
        }
    }
}

@Composable
fun WishTextField(
    value: String,
    label: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label, color = Color.Black) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}
