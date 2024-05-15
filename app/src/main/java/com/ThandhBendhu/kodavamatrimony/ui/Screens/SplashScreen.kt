package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CheckSignedIn

@Composable
fun SplashScreen(navController: NavController,
                 viewModel: KmViewModel
) {
    Box(modifier= Modifier
        .background(MaterialTheme.colorScheme.primary)
        .fillMaxSize(),
        ) {
        CheckSignedIn(viewModel = viewModel,
            navController = navController
        )

    }
}