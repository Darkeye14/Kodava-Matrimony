package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController) {
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
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier

                    .wrapContentSize()
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Details",
                        modifier = Modifier.padding(8.dp),
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.padding(6.dp))

                        Text(
                            text = "This app is developed and maintained by Gyan Ponnappa Ponjanda\n\n" +
                                    "A second year student studying in JSS Science And Technology University (SJCE), Computer Science Dept.\n\n" +
                                    "For any more queries, contact me through mail gyanponnappa@gmail.com \n",
                            modifier = Modifier.padding(8.dp),
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                        )
                    Spacer(modifier = Modifier.padding(6.dp))


                }
            }
        }
    }
}