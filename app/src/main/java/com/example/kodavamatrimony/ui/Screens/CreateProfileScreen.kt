package com.example.kodavamatrimony.ui.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kodavamatrimony.R
import com.example.kodavamatrimony.ui.KmViewModel
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.example.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.CommonImage
import com.example.kodavamatrimony.ui.Utility.CommonProgressBar
import com.example.kodavamatrimony.ui.Utility.navigateTo
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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
    },topBar = {
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
            val imageUrl = viewModel.userData.value?.imageUrl

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
            val genderState = remember {
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
            
            ProfileImage(imageUrl = imageUrl, viewModel = viewModel)

            OutlinedTextField(
                value = genderState.value,
                onValueChange ={
                    genderState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(text = "(Boy/Girl) *Mandatory Field",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                label = {
                    Text(text = "Gender",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )

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
 //Called here
            Button(
                onClick = {
                    viewModel.createOrUpdateProfile(
                        name= nameState.value.text,
                        familyName=familyNameState.value.text,
                        number=numberState.value.text,
                        fathersName = fatherNameState.value.text,
                        mothersName = motherNameState.value.text,
                        age=ageState.value.text,
                        description = descriptionState.value.text,
                        requirement = requirementsState.value.text,
                        gender=genderState.value.text.uppercase(Locale.ROOT)
                    )
                    navigateTo(navController,DestinationScreen.HomeScreen.route)
                    viewModel.onAddedProfile(nameState.value.text)
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



@Composable
fun ProfileImage(
    imageUrl: String?,
    viewModel: KmViewModel
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri ->
        uri?.let {
            viewModel.uploadProfileImage(uri)
        }
    }
    Box(
        modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp),
                colors = CardDefaults.cardColors(Color.Gray)

            ) {
                CommonImage(data = imageUrl)
            }
            Text(text = "Change Profile Picture")
        }
        if (viewModel.inProgress.value){
            CommonProgressBar()
        }
    }
}