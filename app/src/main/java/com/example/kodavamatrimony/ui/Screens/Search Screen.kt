package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kodavamatrimony.data.ProfileItem
import com.example.kodavamatrimony.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier :Modifier = Modifier
){
    Scaffold(
    topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold
            )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
) {
        LazyColumn(modifier = modifier
            .fillMaxSize(),
            contentPadding = it
        ) {

        }
    }
}

@Composable
fun ProfileItem(
    item: ProfileItem,
    modifier: Modifier = Modifier,
){
    Card(
        modifier = modifier,

    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {

        }
    }
}

@Composable
fun ProfilePicture(
     dogIcon: Int,
    modifier: Modifier = Modifier
) {
//    Image(
//        modifier = modifier
//            .size(dimensionResource(R.dimen.image_size))
//            .padding(dimensionResource(R.dimen.padding_small))
//            .clip(MaterialTheme.shapes.small),
//        contentScale = ContentScale.Crop,
//        painter =,
//        contentDescription = null
//    )
}