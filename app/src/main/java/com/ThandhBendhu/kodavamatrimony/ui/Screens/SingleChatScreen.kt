package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CommonProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChatScreen(
    navController: NavController,
    viewModel: KmViewModel,
    chatId : String
) {
    val reply = rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.populateMessages(chatId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {val myUser = viewModel.auth.currentUser?.uid
                    val currentChat = viewModel.chats.value.first{it.chatId == chatId}
                    val chatUser = if (myUser == currentChat.user1.accId) currentChat.user2
                    else currentChat.user1
                    Text(text = chatUser.name)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.primary

    ) {
        if(viewModel.inProgressChat.value){
            CommonProgressBar()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                reverseLayout = true
            ) {
                val messages = viewModel.chatMessages.value
                items (messages){msg->
                    MessageCard(msg = msg.message, time = msg.timeStamp,msg.sendBy, viewModel)
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(6.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = reply.value,
                    onValueChange = {
                        reply.value = it
                    },
                    placeholder = {
                        Text(text = "Type a prompt")
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier
                        .padding()
                        .size(40.dp)
                        .clickable {
                            viewModel.onSendReply(chatId, reply.value)
                            reply.value = ""
                        },
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    contentDescription = "send prompt",
                    tint = MaterialTheme.colorScheme.primary
                )

            }
        }
    }
}
@Composable
fun MessageCard(msg :String?,time : String?,sentBy: String?,viewModel: KmViewModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(),
        colors = if (sentBy == viewModel.auth.currentUser?.uid)
         CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        else
            CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)

    ) {

        if (msg != null && time !=null) {

           Column() {
               Text(text = msg, modifier = Modifier.padding(8.dp))
            Text(text = time,modifier = Modifier.padding(8.dp))
        }
        }

    }
}
