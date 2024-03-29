package com.example.kodavamatrimony.ui.Utility

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen

fun navigateTo(
    navController: NavController,
    route : String
){
    navController.navigate(route){
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CheckSignedIn(viewModel : KmViewModel,
                  navController: NavController
) {
    val alreadySignedIn = remember{ mutableStateOf(false) }
    val signIn = viewModel.signIn.value
    if(signIn && !alreadySignedIn.value ){
        alreadySignedIn.value=true
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinationScreen.HomeScreen.route){
                popUpTo(0)
            }
        }

    }
    else if(!signIn){
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinationScreen.SignUp.route){
                popUpTo(0)
            }
        }
    }

}