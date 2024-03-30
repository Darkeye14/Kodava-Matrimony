package com.example.kodavamatrimony.ui.Navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Screens.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Screens.CreateProfileScreen
import com.example.kodavamatrimony.ui.Screens.HomeScreen
import com.example.kodavamatrimony.ui.Screens.LoginScreen
import com.example.kodavamatrimony.ui.Screens.SignUpScreen
import com.example.kodavamatrimony.ui.Screens.SplashScreen

@Composable
fun KmNavigation() {

//implement Splash Screen

    val navController = rememberNavController()
    var viewModel = hiltViewModel<KmViewModel>()
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreen.route
    ){
        composable(DestinationScreen.SplashScreen.route){
            SplashScreen(navController,viewModel)
        }
        composable(DestinationScreen.SignUp.route){
            SignUpScreen(navController,viewModel)
        }
        composable(DestinationScreen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(DestinationScreen.SearchScreen.route){
            SearchScreen(navController)
        }
        composable(DestinationScreen.Login.route){
            LoginScreen(viewModel,navController)
        }
        composable(DestinationScreen.CreateProfileScreen.route){
            CreateProfileScreen(navController = navController, viewModel = viewModel)
        }

    }
}