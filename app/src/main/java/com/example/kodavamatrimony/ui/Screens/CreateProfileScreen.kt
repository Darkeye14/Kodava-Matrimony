package com.example.kodavamatrimony.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Utility.CommonProgressBar

@Composable
fun CreateProfileScreen(
    navController: NavController,
    viewModel: KmViewModel
) {
    Scaffold(bottomBar = {
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.CREATEPROFILELIST,
            navController = navController,
            )
    },
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(it)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val nameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val familyNameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val fatherNameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val motherNameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val numberState = remember {
                mutableStateOf(TextFieldValue())
            }
            val descriptionState = remember {
                mutableStateOf(TextFieldValue())
            }
            val requirementsState = remember {
                mutableStateOf(TextFieldValue())
            }
            val ageState = remember {
                mutableStateOf(TextFieldValue())
            }
            val focus = LocalFocusManager.current
            Image(painter = painterResource(id = R.drawable.love),
                contentDescription = "Create Profile",
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)

            )

            Text(text = "Create Profile",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
 //Add profile pic
            OutlinedTextField(
                value = nameState.value,
                onValueChange ={
                    nameState.value = it
                },
                placeholder = {
                    Text(text = "Full name including Mane Peda",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "Name",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )

            OutlinedTextField(
                value = familyNameState.value,
                onValueChange ={
                    familyNameState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "Family Name (Mane Peda)",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            OutlinedTextField(
                value = fatherNameState.value,
                onValueChange ={
                    fatherNameState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "Father's Name",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            OutlinedTextField(
                value = motherNameState.value,
                onValueChange ={
                    motherNameState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(text = "Include Tamane",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                label = {
                    Text(text = "Mother's Name",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )

            OutlinedTextField(
                value = numberState.value,
                onValueChange ={
                    numberState.value = it
                },
                label = {
                    Text(text = "Phone Number",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = ageState.value,
                onValueChange ={
                    ageState.value = it
                },
                placeholder = {
                    Text(text = "dd-mm-yyyy",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                label = {
                    Text(text = "Date of Birth",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            OutlinedTextField(
                value = descriptionState.value,
                onValueChange ={
                    descriptionState.value = it
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.description),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                singleLine = false,
                label = {
                    Text(text = "Description",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            OutlinedTextField(
                value = requirementsState.value,
                onValueChange ={
                    requirementsState.value = it
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.requirements),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "Requirements",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            Button(
                onClick = {

                    viewModel.createOrUpdateProfile(
                        nameState.value.text,
                        familyNameState.value.text,
                        numberState.value.text,
                        fatherNameState.value.text,
                        motherNameState.value.text,
                        ageState.value.text,
                        descriptionState.value.text,

                    )
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "CREATE PROFILE")
            }
        }
        if(viewModel.inProgress.value ){
            CommonProgressBar()
        }
    }
}