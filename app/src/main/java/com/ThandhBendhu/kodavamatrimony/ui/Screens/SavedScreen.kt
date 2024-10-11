package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.DestinationScreen
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CommonProgressBar
import com.ThandhBendhu.kodavamatrimony.ui.Utility.ProfileCard
import com.ThandhBendhu.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
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
        if (viewModel.inProgressProfile.value) {
            CommonProgressBar()
        }
        val profiles = viewModel.myBookmarksData.value
        if (profiles.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .height(130.dp)
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.error)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            text = "KODAVA SAMAJA(R) MYSURU",
                            maxLines = 2,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Spacer(modifier = Modifier.size(225.dp))
                Text(
                    text = "No Profiles Available",
                    fontSize = 25.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.error)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center,
                                text = "KODAVA SAMAJA(R) MYSURU",
                                maxLines = 2,
                                fontSize = 25.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
                items(profiles) { profile ->

                    profile.data?.let { it1 ->
                        ProfileCard(
                            profile = it1,
                            onItemClick = {
                                profile.data?.userId?.let {
                                    navigateTo(
                                        navController,
                                        DestinationScreen.SingleBookmarkScreen.createRoute(id = it)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}