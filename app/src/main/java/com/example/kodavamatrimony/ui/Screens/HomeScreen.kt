package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: KmViewModel,

){

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
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(it)
                   .background(MaterialTheme.colorScheme.primaryContainer),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Row(modifier = Modifier.padding(8.dp)) {
                   HomeScreenCard(Modifier.weight(1f),text = "Your Profiles") {
                       viewModel.getMyProfilesData()
                       navigateTo(navController,DestinationScreen.MyProfilesScreen.route)
                   }
                   HomeScreenCard(Modifier.weight(1f),text = "Saved Profiles") {
                       viewModel.getMyBookmarksData()
                       navigateTo(navController,DestinationScreen.SavedScreen.route)
                   }
               }
               Row(
                   modifier = Modifier.padding(8.dp)
               ) {
                   HomeScreenCard(Modifier.weight(1f),text = "View Profiles") {

                       navigateTo(navController,DestinationScreen.SearchScreen.route)
                   }
                   HomeScreenCard(Modifier.weight(1f),text = "Direct Messages") {

                       navigateTo(navController,DestinationScreen.ChatListScreen.route)
                   }
               }
           }
        }

}

@Composable
fun HomeScreenCard(
    modifier: Modifier,
    text:String,
    onClick : ()->Unit,
) {
    Card(modifier = modifier
        .height(250.dp)
        .clickable {
            onClick.invoke()
        }
        .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)) {
        Row(modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                text = text,
                maxLines = 2,
                fontSize =25.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }

}