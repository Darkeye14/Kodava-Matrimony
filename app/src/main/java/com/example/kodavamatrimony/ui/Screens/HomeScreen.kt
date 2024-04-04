package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
    
    BottomNavigationMenu(
        selectedItem = BottomNavigationItem.HOMELIST,
        navController = navController
    )
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                            Text(text = stringResource(id = R.string.app_name))
                },
                    colors = topAppBarColors(
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
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                              navigateTo(navController,DestinationScreen.MyProfilesScreen.route)
                    },
                ) {
                    Text(text = "My Profiles")
                }
                Button(
                    onClick = { },
                ) {
                    Text(text = stringResource(R.string.your_profile))
                }

                Button(
                    onClick = { },
                ) {
                    Text(text = stringResource(R.string.saved_profile))
                }
                Button(
                    onClick = { },
                ) {
                    Text(text = stringResource(R.string.search_profile))
                }

            }
        }

}


