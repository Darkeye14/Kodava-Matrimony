package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.DestinationScreen
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CommonImage
import com.ThandhBendhu.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleMyProfileScreen(
    navController: NavController,
    viewModel: KmViewModel,
    profileId: String
) {
    val currentProfile = viewModel.myProfiles.value.first { it.userId == profileId }
    viewModel.downloadSingleProfileImage(profileId)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DeleteButton {
                    navigateTo(
                        navController,
                        DestinationScreen.DeleteScreen.createRoute(id = profileId)
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CommonImage(
                    data = viewModel.singleProfileBmp.value,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                )
            }

            Display(textName = "Family Name :", text = currentProfile.familyName)
            Display(textName = "Name :", text = currentProfile.name)
            Display(textName = "Father's Name :", text = currentProfile.fathersName)
            Display(textName = "Mother's Name :", text = currentProfile.mothersName)
            Display(textName = "Gender :", text = currentProfile.gender)
            Display(textName = "Height :", text = currentProfile.height)
            Display(textName = "DOB :", text = currentProfile.age)
            Display(textName = "Time Of Birth :", text = currentProfile.timeOfBirth)
            Display(textName = "Siblings :", text = currentProfile.siblings)
            Display(textName = "Qualification :", text = currentProfile.education)
            Display(textName = "Profession Details :", text = currentProfile.profession)
            Display(textName = "Settled Place :", text = currentProfile.location)
            Display(textName = "Native Place :", text = currentProfile.nativePlace)
            Display(textName = "Property :", text = currentProfile.property)
            Display(textName = "Marital Status :", text = currentProfile.maritalStatus)
            Display(textName = "Contact Number :", text = currentProfile.number)
            Display(textName = "Additional Information:", text = currentProfile.description)
            Display(textName = "Looking For :", text = currentProfile.requirement)

        }
    }
}
@Composable
fun DeleteButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Default.Delete, "Extended floating action button.") },
        text = { Text(text = "Delete") },
        containerColor = MaterialTheme.colorScheme.primary
    )
}
