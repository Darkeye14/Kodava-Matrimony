package com.example.kodavamatrimony.ui.Screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.navigateTo

enum class BottomNavigationItem (
    val icon :Int,
    val destinationScreen: DestinationScreen
){
    HOMELIST(R.drawable.home_button, DestinationScreen.HomeScreen),
    SEARCHLIST(R.drawable.loupe, DestinationScreen.SearchScreen),
    CREATEPROFILELIST(R.drawable.resume, DestinationScreen.CreateProfileScreen),

}
@Composable
fun BottomNavigationMenu(
    selectedItem : BottomNavigationItem,
    navController: NavController,

) {
    Row(
      modifier = Modifier
          .padding(4.dp)
          .fillMaxWidth()
          .wrapContentHeight()
          .background(MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        for(item in BottomNavigationItem.entries){
            Image(
                painter = painterResource(id = item.icon)
                , contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .weight(1f)
                    .clickable {
                        navigateTo(navController,item.destinationScreen.route)
                    },
                colorFilter = if(item == selectedItem){
                    ColorFilter.tint(MaterialTheme.colorScheme.primary)
                }
                else
                ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
        }
    }
}