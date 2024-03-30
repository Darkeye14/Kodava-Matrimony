import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.kodavamatrimony.ui.Screens.BottomNavigationItem
import com.example.kodavamatrimony.ui.Screens.BottomNavigationMenu

@Composable
fun SearchScreen(
    navController : NavController
) {
    Scaffold(
        bottomBar = {
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.SEARCHLIST,
                navController = navController
            )
        }
    ) {
        LazyColumn(contentPadding = it) {

        }
    }
}