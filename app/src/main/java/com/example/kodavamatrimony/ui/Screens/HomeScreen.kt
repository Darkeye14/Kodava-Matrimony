package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.theme.KodavaMatrimonyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                            Text(text = stringResource(id = R.string.app_name))
                },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { },
                ) {
                    Text(text = stringResource(R.string.make_profile))
                }
                Button(
                    onClick = { },
                ) {
                    Text(text = stringResource(R.string.your_profile))
                }

                Button(
                    onClick = { },
                ) {
                    Text(text = stringResource(R.string.saved_profile))
                }
                Button(
                    onClick = { },
                ) {
                    Text(text = stringResource(R.string.search_profile))
                }

            }
        }

}


@Preview
@Composable
fun Screen(
    modifier: Modifier = Modifier
) {
    KodavaMatrimonyTheme {
        HomeScreen()
    }
}