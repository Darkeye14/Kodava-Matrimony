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
fun SingleBookmarkScreen(
    navController: NavController,
    viewModel: KmViewModel,
    profileId: String
) {
    val currentProfile = viewModel.myBookmarksData.value.first { it.data?.userId == profileId }
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

            Display(textName = "Family Name :", text = currentProfile.data?.familyName)
            Display(textName = "Name :", text = currentProfile.data?.name)
            Display(textName = "Father's Name :", text = currentProfile.data?.fathersName)
            Display(textName = "Mother's Name :", text = currentProfile.data?.mothersName)
            Display(textName = "Gender :", text = currentProfile.data?.gender)
            Display(textName = "Height :", text = currentProfile.data?.height)
            Display(textName = "DOB :", text = currentProfile.data?.age)
            Display(textName = "Time Of Birth :", text = currentProfile.data?.timeOfBirth)
            Display(textName = "Siblings :", text = currentProfile.data?.siblings)
            Display(textName = "Qualification :", text = currentProfile.data?.education)
            Display(textName = "Profession Details :", text = currentProfile.data?.profession)
            Display(textName = "Settled Place :", text = currentProfile.data?.location)
            Display(textName = "Native Place :", text = currentProfile.data?.nativePlace)
            Display(textName = "Property :", text = currentProfile.data?.property)
            Display(textName = "Marital Status :", text = currentProfile.data?.maritalStatus)
            Display(textName = "Contact Number :", text = currentProfile.data?.number)
            Display(textName = "Additional Information:", text = currentProfile.data?.description)
            Display(textName = "Looking For :", text = currentProfile.data?.requirement)
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
