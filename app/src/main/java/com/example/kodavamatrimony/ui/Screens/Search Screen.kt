
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.kodavamatrimony.ui.Utility.CommonProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController : NavController,
    viewModel : KmViewModel
) {
    val pfId = remember {
        mutableStateOf("")
    }
    Scaffold(
        bottomBar = {
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.SEARCHLIST,
                navController = navController
            )
        },
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
        }
    ) {PaddingValues->
        if(viewModel.inProgressProfile.value){
            CommonProgressBar()
        }
        else{

  // Pass this in onClick in icon clickabe for bookmark

            val profiles = viewModel.profiles.value
            val userData = viewModel.userData.value
            val onSaveProfile: (String)->Unit ={
                viewModel.onAddedProfile(it)
            }
            val showDialog = remember {
                mutableStateOf(false)
            }
            val onFabClick :()->Unit = { showDialog.value = true}
            val onDismiss :()->Unit = { showDialog.value = false}
            if(profiles.isEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                 Text(text = "No Profiles Available")
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