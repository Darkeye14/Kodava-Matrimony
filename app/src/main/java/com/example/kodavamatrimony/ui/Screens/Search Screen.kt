import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
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
    ) {
        if(viewModel.inProgressProfile.value){
            CommonProgressBar()
        }
        else{
            val profiles = viewModel.profiles.value
            val onSaveProfile: (String)->Unit ={
                viewModel.onSaveProfile(it)
            }
            if(profiles.isEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                 Text(text = "No Profiles Available")
                }
            }
        }
        






        LazyColumn(contentPadding = it) {

        }
    }
}