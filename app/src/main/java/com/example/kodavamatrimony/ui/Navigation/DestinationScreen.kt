package com.example.kodavamatrimony.ui.Navigation

sealed class DestinationScreen(var route : String) {
    data object SignUp:DestinationScreen("signup")
    data object BottomNavigationMenu:DestinationScreen("bottomNavigationMenu")
    data object Login:DestinationScreen("login")
    data object HomeScreen:DestinationScreen("homeScreen")
    data object SplashScreen:DestinationScreen("splashScreen")
    data object SearchScreen:DestinationScreen("searchScreen")
    data object SavedScreen:DestinationScreen("savedScreen")
    data object CreateProfileScreen:DestinationScreen("createProfileScreen")
    data object MyProfilesScreen:DestinationScreen("myProfilesScreen")
    data object ChatListScreen:DestinationScreen("chatListScreen")
    data object AfterLoginScreen:DestinationScreen("afterLoginScreen")
    data object SingleProfileScreen:DestinationScreen("singleProfileScreen/{profileId}"){
       fun createRoute(id : String) = "singleProfileScreen/$id"
    }
    data object SingleChatScreen:DestinationScreen("singleChatScreen/{chatId}"){
       fun createRoute(id : String) = "singleChatScreen/$id"
    }

    data object DeleteScreen:DestinationScreen("deleteScreen/{profileId}"){
       fun createRoute(id : String) = "deleteScreen/$id"
    }
    data object PhotoViewScreen:DestinationScreen("photoViewScreen/{profileId}"){
        fun createRoute(id : String) = "singleProfileScreen/$id"
    }

}