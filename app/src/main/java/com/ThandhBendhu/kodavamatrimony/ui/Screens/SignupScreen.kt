package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.DestinationScreen
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CommonProgressBar
import com.ThandhBendhu.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: KmViewModel
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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val emailState = remember {
                mutableStateOf(TextFieldValue())
            }
            val passwordState = remember {
                mutableStateOf(TextFieldValue())
            }
            val nameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val focus = LocalFocusManager.current
            Image(
                painter = painterResource(id = R.drawable.parents),
                contentDescription = "signIn",
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )

            Text(
                text = "Sign Up",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value = nameState.value,
                singleLine = true,
                onValueChange = {
                    nameState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = "Name",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )

            OutlinedTextField(
                value = emailState.value,
                singleLine = true,
                onValueChange = {
                    emailState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = "Email",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                placeholder = {
                    Text(text = "Ex: abcd@gmail.com")
                }
            )
            OutlinedTextField(
                value = passwordState.value,
                singleLine = true,
                onValueChange = {
                    passwordState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(text = " Ex: abcd1234")
                },
                label = {
                    Text(
                        text = "Password (Alphanumeric)",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            Button(
                onClick = {
                    if(nameState.value.text.trim().isNotEmpty() && emailState.value.text.trim().isNotEmpty() && passwordState.value.text.trim().isNotEmpty()) {
                        viewModel.signUp1(
                            nameState.value.text.trim(),
                            emailState.value.text.trim(),
                            passwordState.value.text.trim(),
                            navController
                        )
                        //careful
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "SIGN UP")
            }
            Text(
                text = "Already a user ? Go to login",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, DestinationScreen.Login.route)
                    }
            )

        }
        if (viewModel.inProgress.value) {
            CommonProgressBar()
        }
    }
}