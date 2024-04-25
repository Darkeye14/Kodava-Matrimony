package com.example.kodavamatrimony.ui.Screens

import android.widget.PopupMenu.OnDismissListener
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.CommonImage
import com.example.kodavamatrimony.ui.Utility.navigateTo

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

                BookmarkButton { viewModel.onBookmark(profileId) }
            }

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
            Button(
                onClick = {
                    show.value =true
                },
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.padding(bottom = 12.dp, top = 6.dp)
            ) {
                Text(text = "Anonymous Chat", fontSize = 20.sp)
            }
            if (show.value){
                Dialog( navController = navController , viewModel = viewModel)
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
fun DeleteButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Default.Delete, "Extended floating action button.") },
        text = { Text(text = "Delete") },
        containerColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun Dialog(
    navController: NavController,
    viewModel: KmViewModel
) {
    val chatName = remember {
        mutableStateOf("")
    }
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
                        navigateTo(navController,DestinationScreen.ChatListScreen.route)

                    }) {
                    Text(text = "OK")
                }
            },
            title = {
                Text(text = "Direct Message", fontWeight = FontWeight.Bold)
            },
            text = {
                OutlinedTextField(
                    value = chatName.value,
                    onValueChange = { chatName.value = it },
                    label = { Text(text = "Give a unique name to this chat") }
                )
            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}