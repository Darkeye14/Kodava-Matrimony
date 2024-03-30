package com.example.kodavamatrimony.ui.Navigation

sealed class DestinationScreen(var route : String) {
    data object SignUp:DestinationScreen("signup")
    data object BottomNavigationMenu:DestinationScreen("bottomNavigationMenu")
    data object Login:DestinationScreen("login")
    data object HomeScreen:DestinationScreen("homeScreen")
    data object SplashScreen:DestinationScreen("splashScreen")
    data object SearchScreen:DestinationScreen("searchScreen")
    data object CreateProfileScreen:DestinationScreen("createProfileScreen")
    data object SingleProfileScreen:DestinationScreen("singleProfileScreen/{profileId}"){
       fun createRoute(id : String) = "singleProfileScreen/$id"
    }
    data object PhotoViewScreen:DestinationScreen("photoViewScreen/{profileId}"){
        fun createRoute(id : String) = "singleProfileScreen/$id"
    }

}