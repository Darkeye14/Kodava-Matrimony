package com.ThandhBendhu.kodavamatrimony.ui.Navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Screens.ChatListScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.CreateProfileScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.DeleteScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.DetailsScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.HomeScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.LoginScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.MyProfilesSscreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.SavedScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.SignUpScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.SingleBookmarkScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.SingleChatScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.SingleMyProfileScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.SingleProfileScreen
import com.ThandhBendhu.kodavamatrimony.ui.Screens.SplashScreen

@Composable
fun KmNavigation() {

//implement Splash Screen

    val navController = rememberNavController()
    val viewModel = hiltViewModel<KmViewModel>()
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreen.route
    ){
        composable(DestinationScreen.SplashScreen.route){
            SplashScreen(navController,viewModel)
        }
        composable(DestinationScreen.DetailsScreen.route){
            DetailsScreen(navController)
        }
        composable(DestinationScreen.SignUp.route){
            SignUpScreen(navController,viewModel)
        }
        composable(DestinationScreen.HomeScreen.route){
            HomeScreen(navController,viewModel)
        }
        composable(DestinationScreen.SingleProfileScreen.route){
            val profileId = it.arguments?.getString("profileId")
            profileId?.let {
                SingleProfileScreen(navController,viewModel, profileId)
            }

        }
        composable(DestinationScreen.SingleBookmarkScreen.route){
            val profileId = it.arguments?.getString("profileId")
            profileId?.let {
                SingleBookmarkScreen(navController,viewModel, profileId)
            }

        }
        composable(DestinationScreen.SingleMyProfileScreen.route){
            val profileId = it.arguments?.getString("profileId")
            profileId?.let {
                SingleMyProfileScreen(navController,viewModel, profileId)
            }

        }
        composable(DestinationScreen.SingleChatScreen.route){
            val chatId = it.arguments?.getString("chatId")
            chatId?.let {
                SingleChatScreen(navController,viewModel, chatId)
            }

        }
        composable(DestinationScreen.DeleteScreen.route){
            val profileId = it.arguments?.getString("profileId")
            profileId?.let {
                DeleteScreen(navController,viewModel, profileId)
            }
        }

        composable(DestinationScreen.MyProfilesScreen.route){
           MyProfilesSscreen(navController,viewModel)
        }

        composable(DestinationScreen.SearchScreen.route){
            val gender = it.arguments?.getString("gender")
            gender?.let {
                SearchScreen(navController,viewModel,gender)
            }
        }
        composable(DestinationScreen.Login.route){
            LoginScreen(viewModel,navController)
        }
        composable(DestinationScreen.ChatListScreen.route){
            ChatListScreen(viewModel,navController)
        }
        composable(DestinationScreen.SavedScreen.route){
            SavedScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.CreateProfileScreen.route){
            CreateProfileScreen(navController = navController, viewModel = viewModel)
        }

    }
}