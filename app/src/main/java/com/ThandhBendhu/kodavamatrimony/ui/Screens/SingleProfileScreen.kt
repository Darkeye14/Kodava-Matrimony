package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun SingleProfileScreen(
    navController: NavController,
    viewModel: KmViewModel,
    profileId: String
) {
    val currentProfile = viewModel.profiles.value.first { it.userId == profileId }
    val show = remember {
        mutableStateOf(false)
    }
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
                BookmarkButton {
                    viewModel.onBookmark(profileId)
                navigateTo(navController,DestinationScreen.SavedScreen.route)
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
            Button(
                onClick = {
                    show.value =true
                },
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.padding(bottom = 12.dp, top = 6.dp)
            ) {
                Text(text = "Message", fontSize = 20.sp)
            }
            if (show.value){
                Dialog( profileId,navController = navController , viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Display(
    textName: String,
    text: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {
            Text(
                text = textName,
                modifier = Modifier
                    .padding(12.dp),
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,

                )
            Text(
                text = if (text != null) {
                    text
                } else {
                    "Not Specified"
                },
                modifier = Modifier
                    .padding(16.dp),
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                minLines = 1

            )
        }
    }
}

@Composable
fun BookmarkButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Default.AddCircle, "Extended floating action button.") },
        text = { Text(text = "Bookmark") },
        containerColor = MaterialTheme.colorScheme.primary
    )
}


@Composable
fun Dialog(
    profileId : String,
    navController: NavController,
    viewModel: KmViewModel
) {

    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
          //viewmodel
                        viewModel.onAddChat( profileId )
                        navigateTo(navController,DestinationScreen.ChatListScreen.route)

                    }) {
                    Text(text = "OK")
                }
            },
            title = {
                Text(text = "Direct Message", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "Illegal or irrational use of others info will lead to permanent suspension and may lead to legal issues ")
            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}