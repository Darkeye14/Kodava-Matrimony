package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.CheckSignedIn

@Composable
fun SplashScreen(navController: NavController,
                 viewModel: KmViewModel
) {
    Box(modifier= Modifier
        .fillMaxSize(),
        ) {
        Image(painter = painterResource(id = R.drawable._0ec0824746b17f1b234c300bce48bcf),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
        CheckSignedIn(viewModel = viewModel,
            navController = navController
        )

    }
}