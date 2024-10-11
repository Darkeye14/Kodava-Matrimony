package com.ThandhBendhu.kodavamatrimony.ui.Navigation

sealed class DestinationScreen(var route : String) {
    data object SignUp:DestinationScreen("signup")
    data object Login:DestinationScreen("login")
    data object HomeScreen:DestinationScreen("homeScreen")
    data object SplashScreen:DestinationScreen("splashScreen")
    data object SearchScreen:DestinationScreen("searchScreen/{gender}"){
        fun createRoute(gender : String) = "searchScreen/$gender"
    }
    data object SavedScreen:DestinationScreen("savedScreen")
    data object CreateProfileScreen:DestinationScreen("createProfileScreen")
    data object MyProfilesScreen:DestinationScreen("myProfilesScreen")
    data object ChatListScreen:DestinationScreen("chatListScreen")
    data object DetailsScreen:DestinationScreen("detailsScreen")
    data object SingleBookmarkScreen:DestinationScreen("singleBookmarkScreen/{profileId}"){
       fun createRoute(id : String) = "singleBookmarkScreen/$id"
    }
    data object SingleProfileScreen:DestinationScreen("singleProfileScreen/{profileId}"){
       fun createRoute(id : String) = "singleProfileScreen/$id"
    }
    data object SingleMyProfileScreen:DestinationScreen("singleMyProfileScreen/{profileId}"){
       fun createRoute(id : String) = "singleMyProfileScreen/$id"
    }
    data object SingleChatScreen:DestinationScreen("singleChatScreen/{chatId}"){
       fun createRoute(id : String) = "singleChatScreen/$id"
    }
    data object DeleteScreen:DestinationScreen("deleteScreen/{profileId}"){
       fun createRoute(id : String) = "deleteScreen/$id"
    }


}