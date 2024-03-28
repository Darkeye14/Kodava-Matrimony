package com.example.kodavamatrimony.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Screens.LoginScreen
import com.example.kodavamatrimony.ui.Screens.SignUpScreen

@Composable
fun KmNavigation() {
    val navController = rememberNavController()
    var viewModel = hiltViewModel<KmViewModel>()
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SignUp.route
    ){
        composable(DestinationScreen.SignUp.route){
            SignUpScreen(navController,viewModel)
        }
        composable(DestinationScreen.Login.route){
            LoginScreen()
        }
    }
}