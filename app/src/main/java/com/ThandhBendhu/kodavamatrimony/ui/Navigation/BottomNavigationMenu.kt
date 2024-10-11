package com.ThandhBendhu.kodavamatrimony.ui.Navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.ui.Utility.Alert
import com.ThandhBendhu.kodavamatrimony.ui.Utility.navigateTo

enum class BottomNavigationItem(
    val icon: Int,
    val destinationScreen: DestinationScreen
) {
    HOMELIST(R.drawable.home, DestinationScreen.HomeScreen),
    SEARCHLIST(R.drawable.people, DestinationScreen.SearchScreen),
    CREATEPROFILELIST(R.drawable.curriculum_vitae, DestinationScreen.CreateProfileScreen),

}

@Composable
fun BottomNavigationMenu(
    selectedItem: BottomNavigationItem,
    navController: NavController,
) {
    val search = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier

            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        for (item in BottomNavigationItem.entries) {
            
            Image(
                painter = painterResource(id = item.icon), contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(4.dp)
                    .weight(1f)
                    .clickable {
                        if (item != BottomNavigationItem.SEARCHLIST) {
                            navigateTo(navController, item.destinationScreen.route)
                        } else {
                            search.value = true
                        }
                    },
                colorFilter = if (item == selectedItem) {
                    ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer)
                } else
                    ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            if (search.value){
                Alert(navController = navController)

            }
        }
    }
}