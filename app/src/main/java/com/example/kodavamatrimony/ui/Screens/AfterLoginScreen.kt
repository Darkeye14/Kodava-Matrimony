package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import com.example.kodavamatrimony.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AfterLoginScreen(

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
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AlertDialog(
                onDismissRequest = {

                },
                confirmButton = {
                    Button(
                        onClick = {

                        }) {
                        Text(text = "OK")
                    }
                },
                title = {
                    Text(text = "Restart The App", fontWeight = FontWeight.Bold)
                },
                text = {
                    Text(text = "Terminate the app and restart it for its proper working ")
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}