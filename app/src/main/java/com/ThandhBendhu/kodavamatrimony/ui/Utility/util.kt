package com.ThandhBendhu.kodavamatrimony.ui.Utility

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.data.UserData
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.DestinationScreen

fun navigateTo(
    navController: NavController,
    route: String
) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun CheckSignedIn(
    viewModel: KmViewModel,
    navController: NavController
) {
    val alreadySignedIn = remember { mutableStateOf(false) }
    val signIn = viewModel.signIn.value
    if (signIn && !alreadySignedIn.value) {
        alreadySignedIn.value = true
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinationScreen.HomeScreen.route) {
                popUpTo(0)
            }
        }

    } else if (!signIn) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinationScreen.SignUp.route) {
                popUpTo(0)
            }
        }
    }
}


@Composable
fun CommonImage(
    data: Bitmap?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {

    AsyncImage(
        model = data,
        contentDescription = null,
        modifier.wrapContentSize(),
        contentScale = contentScale
    )
}

@Composable
fun ProfileCard(
    profile: UserData,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(312.dp)
            .padding(12.dp)
            .clickable {
                onItemClick.invoke()
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {

            val resId = if (profile.gender.equals("Female",true)) {
                R.drawable.girl
            } else{
                R.drawable.boy
            }
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .size(175.dp)
                    .padding(12.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Inside
            )

            Text(
                text = profile.name ?: "Anonymous",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier
                    .padding(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = profile.gender ?: "-Gender-",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(12.dp)
                )
                Text(
                    text = profile.age ?: "-Age-",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }

        }
    }
}

@Composable
fun ChatCard(
    name: String,
    onItemClick: () -> Unit,
    onDeleteClick:()->Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .height(155.dp)
            .padding(12.dp)
            .clickable {
                onItemClick.invoke()
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center

        ) {
            Text( textAlign = TextAlign.Start,
                text = name,
                modifier= Modifier.padding(4.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp
            )
            Row(
                modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                DeleteChatButton { onDeleteClick.invoke() }
            }
        }
    }
}

@Composable
fun DeleteChatButton(
    onClick :()->Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onClick.invoke()},
        icon = { Icon(Icons.Default.Delete, "Extended floating action button.") },
        text = { Text(text = "Delete") },
        containerColor = MaterialTheme.colorScheme.errorContainer
    )
}


@Composable
fun Alert(
    navController: NavController
) {
    val openDialog = remember { mutableStateOf(true) }
    val selectedOption = remember { mutableStateOf("Male") }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        navigateTo(navController, DestinationScreen.SearchScreen.createRoute(gender = selectedOption.value))
                        openDialog.value = false
                    }) {
                    Text(text = "OK")
                }
            },
            title = {
                Text(text = "Please Select", fontWeight = FontWeight.Bold)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            text = {
                Column(
                    modifier = Modifier
                        //         .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ){

                        Column{
                            Text("Male")
                            RadioButton(
                                selected = selectedOption.value == "Male",
                                onClick = {
                                    selectedOption.value = "Male"
                                }
                            )
                        }
                        Column{
                            Text("Female")
                            RadioButton(
                                selected = selectedOption.value == "Female",
                                onClick = {
                                    selectedOption.value = "Female"


                                }
                            )
                        }
                    }

                }
            }
        )
    }
}