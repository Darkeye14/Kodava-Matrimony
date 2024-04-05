package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.CommonProgressBar
import com.example.kodavamatrimony.ui.Utility.ProfileCard
import com.example.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfilesSscreen(
    navController: NavController,
    viewModel: KmViewModel
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.HOMELIST,
                navController = navController
            )
        }
    ) {
        if(viewModel.inProgressProfile.value){
            CommonProgressBar()
        }
        val profiles = viewModel.profiles.value
        val userData = viewModel.userData.value
        if (profiles.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No Profiles Available")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                items(profiles){ profile ->
                    val profileUser =
                        if(profile.user1.userId ==userData?.userId){
                            profile.user2
                        } else{
                            profile.user1
                        }
                    ProfileCard(
                        profile = profileUser,
                        onItemClick = {
                            profile.profileId?.let {
                                navigateTo(navController, DestinationScreen.SingleProfileScreen.createRoute(id = it))
                            }
                        }
                    )
                }
            }
        }
    }
}