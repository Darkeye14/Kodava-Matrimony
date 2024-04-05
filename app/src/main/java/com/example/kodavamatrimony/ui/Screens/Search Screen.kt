
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun SearchScreen(
    navController : NavController,
    viewModel : KmViewModel
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
                selectedItem = BottomNavigationItem.SEARCHLIST,
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

                    ProfileCard(
                        profile = profile,
                        onItemClick = {
                            profile.userId?.let {
                                navigateTo(navController, DestinationScreen.SingleProfileScreen.createRoute(id = it))
                            }
                        },
                        onBmkClick = {
                            profile.userId?.let {
                                viewModel.onBookmark(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FAB(
    showDialog : Boolean,
    onFabClick :()-> Unit,
    onDismiss : ()-> Unit,
    onSaveProfile : (String)->Unit
){
    val addProfileMember = remember {
        mutableStateOf("")
    }
    if(showDialog){
        AlertDialog(
            onDismissRequest = {
                onDismiss.invoke()
            addProfileMember.value=""
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSaveProfile(addProfileMember.value)
                    }) {
                    Text(text = "Save")
                }
            },
            title = {
                Text(text = "Save Profile")
            },
            text = {
                OutlinedTextField(
                    value = addProfileMember.value,
                    onValueChange = {
                        addProfileMember.value = it
                    },
                    colors = OutlinedTextFieldDefaults.colors(MaterialTheme.colorScheme.onSecondary)
                )
            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}