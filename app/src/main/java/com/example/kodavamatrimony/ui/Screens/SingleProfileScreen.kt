package com.example.kodavamatrimony.ui.Screens

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Utility.CommonImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleProfileScreen(
    navController: NavController,
    viewModel: KmViewModel,
    profileId: String
) {
    val currentProfile = viewModel.profiles.value.first { it.userId == profileId }

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
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CommonImage(
                    data = currentProfile.imageUrl,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                )
            }

            Display(textName = "Family Name :", text = currentProfile.familyName)
            Display(textName = "Name :", text = currentProfile.name)
            Display(textName = "Gender :", text = currentProfile.gender)
            Display(textName = "DOB :", text = currentProfile.age)
            Display(textName = "Father's Name :", text = currentProfile.fathersName)
            Display(textName = "Mother's Name :", text = currentProfile.mothersName)
            Display(textName = "Contact Number :", text = currentProfile.number)
            Display(textName = "Additional Information:", text = currentProfile.description)
            Display(textName = "Looking For :", text = currentProfile.requirement)


        }
    }


}

@Composable
fun Display(
    textName : String,
    text : String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ){
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