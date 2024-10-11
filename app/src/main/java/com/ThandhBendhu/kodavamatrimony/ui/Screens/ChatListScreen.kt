package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.DestinationScreen
import com.ThandhBendhu.kodavamatrimony.ui.Utility.ChatCard
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CommonProgressBar
import com.ThandhBendhu.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    viewModel: KmViewModel,
    navController: NavController
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
        },
        bottomBar = {
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.SEARCHLIST,
                navController = navController
            )
        }
    ) {
        viewModel.depopulateMessages()
        if (viewModel.inProgressProfile.value) {
            CommonProgressBar()
        }
        val showMessage = remember {
            mutableStateOf(false)
        }
        val profiles = viewModel.profiles.value
        if (profiles.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .height(130.dp)
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.error)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            text = "KODAVA SAMAJA(R) MYSURU",
                            maxLines = 2,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Spacer(modifier = Modifier.size(225.dp))
                Text(
                    text = "No Profiles Available",
                    fontSize = 25.sp
                )
            }
        } else {
            val chats = viewModel.chats.value
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.error)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center,
                                text = "KODAVA SAMAJA(R) MYSURU",
                                maxLines = 2,
                                fontSize = 25.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
                items(chats) { chat ->
                    val chatUser =
                        if (chat.user1.accId == viewModel.auth.currentUser?.uid)
                            chat.user2
                        else
                            chat.user1

                    chat.chatId?.let {
                        ChatCard(
                            name = chatUser.name,
                            onDeleteClick = {
                                showMessage.value = true
                            },
                            onItemClick = {
                                navigateTo(
                                    navController,
                                    DestinationScreen.SingleChatScreen.createRoute(it)
                                )
                            }
                        )
                        if (showMessage.value) {
                            DeleteChatDialog(viewModel = viewModel, chatId = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteChatDialog(
    viewModel: KmViewModel,
    chatId: String
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onDeleteChat(chatId)
                        openDialog.value = false
                    }) {
                    Text(text = "OK")
                }
            },
            title = {
                Text(text = "Delete", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    text = "Confirm that you want to delete this chat by clicking on Ok!",
                    fontWeight = FontWeight.SemiBold
                )
            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}