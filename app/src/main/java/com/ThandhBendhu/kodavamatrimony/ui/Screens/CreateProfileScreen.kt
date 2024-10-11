package com.ThandhBendhu.kodavamatrimony.ui.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ThandhBendhu.kodavamatrimony.R
import com.ThandhBendhu.kodavamatrimony.data.ToggleableInfo
import com.ThandhBendhu.kodavamatrimony.ui.KmViewModel
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationItem
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.BottomNavigationMenu
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.DestinationScreen
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CommonImage
import com.ThandhBendhu.kodavamatrimony.ui.Utility.CommonProgressBar
import com.ThandhBendhu.kodavamatrimony.ui.Utility.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    navController: NavController,
    viewModel: KmViewModel
) {
    Scaffold(
        bottomBar = {
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.CREATEPROFILELIST,
                navController = navController,
            )
        },
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
            // changed color from surface to primCont
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(it)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = remember {
                mutableStateOf("")
            }

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
                mutableStateOf("Male")
            }
            val requirementsState = remember {
                mutableStateOf(TextFieldValue())
            }
            val ageState = remember {
                mutableStateOf(TextFieldValue())
            }
            val educationState = remember {
                mutableStateOf(TextFieldValue())
            }
            val timeOfBirthState = remember {
                mutableStateOf(TextFieldValue())
            }
            val settledPlaceState = remember {
                mutableStateOf(TextFieldValue())
            }
            val siblingsState = remember {
                mutableStateOf(TextFieldValue())
            }
            val propertyState = remember {
                mutableStateOf(TextFieldValue())
            }
            val nativeState = remember {
                mutableStateOf(TextFieldValue())
            }
            val heightState = remember {
                mutableStateOf(TextFieldValue())
            }
            val maritalState = remember {
                mutableStateOf(TextFieldValue())
            }
            val professionState = remember {
                mutableStateOf(TextFieldValue())
            }


            val radioButtons = remember {
                mutableStateListOf(
                    ToggleableInfo(

                        isChecked = true,
                        text = "Male"
                    ),
                    ToggleableInfo(
                        isChecked = false,
                        text = "Female"
                    )
                )
            }

            Image(
                painter = painterResource(id = R.drawable.love),
                contentDescription = "Create Profile",
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)

            )

            Text(
                text = "Create Profile",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            //Add profile pic

            imageUrl.value = ProfileImage(viewModel = viewModel)
            Spacer(modifier = Modifier.padding(12.dp))

            radioButtons.forEachIndexed { index, info ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            radioButtons.replaceAll {
                                it.copy(
                                    isChecked = it.text == info.text
                                )
                            }
                        }
                        .padding(end = 16.dp)
                ) {

                    RadioButton(
                        selected = info.isChecked,
                        onClick = {
                            radioButtons.replaceAll {
                                it.copy(
                                    isChecked = it.text == info.text
                                )
                            }
                            genderState.value = info.text
                        }
                    )
                    Text(text = info.text)
                }
            }
            
            Text(
                text = "* None of the fields are mandatory!!",
                color = MaterialTheme.colorScheme.error
            )

            OutlinedTextField(
                value = nameState.value,
                singleLine = true,
                onValueChange = {
                    nameState.value = it
                },
                placeholder = {
                    Text(
                        text = "Full name including Mane Peda",
                        modifier = Modifier
                            .padding(8.dp)
                    )
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
                value = familyNameState.value,
                singleLine = true,
                onValueChange = {
                    familyNameState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = "Family Name (Mane Peda)",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            OutlinedTextField(
                value = fatherNameState.value,
                singleLine = true,
                onValueChange = {
                    fatherNameState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = "Father's Name",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            OutlinedTextField(
                value = motherNameState.value,
                singleLine = true,
                onValueChange = {
                    motherNameState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(
                        text = "Include Thamane",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                label = {
                    Text(
                        text = "Mother's Name",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )

            OutlinedTextField(
                value = numberState.value,
                singleLine = true,
                onValueChange = {
                    numberState.value = it
                },
                label = {
                    Text(
                        text = "Phone Number",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = ageState.value,
                singleLine = true,
                onValueChange = {
                    ageState.value = it
                },
                placeholder = {
                    Text(
                        text = "dd-mm-yyyy",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                label = {
                    Text(
                        text = "Date of Birth",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = timeOfBirthState.value,
                singleLine = true,
                onValueChange = {
                    timeOfBirthState.value = it
                },
                placeholder = {
                    Text(
                        text = "Hr:Min:Sec AM/PM",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                label = {
                    Text(
                        text = "Time Of Birth",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = heightState.value,
                singleLine = true,
                onValueChange = {
                    heightState.value = it
                },
                label = {
                    Text(
                        text = "Height",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            OutlinedTextField(
                value = siblingsState.value,
                singleLine = true,
                onValueChange = {
                    siblingsState.value = it
                },
                label = {
                    Text(
                        text = "Siblings",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = maritalState.value,
                onValueChange = {
                    maritalState.value = it
                },
                label = {
                    Text(
                        text = "Marital Status",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            OutlinedTextField(
                value = nativeState.value,
                onValueChange = {
                    nativeState.value = it
                },
                label = {
                    Text(
                        text = "Native Place",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            OutlinedTextField(
                value = educationState.value,
                onValueChange = {
                    educationState.value = it
                },
                label = {
                    Text(
                        text = "Qualification",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            OutlinedTextField(
                value = professionState.value,
                onValueChange = {
                    professionState.value = it
                },
                label = {
                    Text(
                        text = "Profession Details",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            OutlinedTextField(
                value = settledPlaceState.value,
                onValueChange = {
                    settledPlaceState.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = "Settled Place",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )

            OutlinedTextField(
                value = propertyState.value,
                onValueChange = {
                    propertyState.value = it
                },
                placeholder = {
                    Text(
                        text = "Optional",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                label = {
                    Text(
                        text = "Property info",
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
                onValueChange = {
                    descriptionState.value = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.description),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                singleLine = false,
                label = {
                    Text(
                        text = "More Info",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            OutlinedTextField(
                value = requirementsState.value,
                onValueChange = {
                    requirementsState.value = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.requirements),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                singleLine = false,

                label = {
                    Text(
                        text = "Partner Preference",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            )
            //Called here
            Button(
                onClick = {
                    viewModel.createProfile(
                        name = nameState.value.text,
                        familyName = familyNameState.value.text,
                        number = numberState.value.text,
                        fathersName = fatherNameState.value.text,
                        mothersName = motherNameState.value.text,
                        age = ageState.value.text,
                        description = descriptionState.value.text,
                        requirement = requirementsState.value.text,
                        gender = genderState.value,
                        settledPlace = settledPlaceState.value.text,
                        education = educationState.value.text,
                        timeOfBirth = timeOfBirthState.value.text,
                        siblings = siblingsState.value.text,
                        property = propertyState.value.text,
                        maritalStatus = maritalState.value.text,
                        height = heightState.value.text,
                        native = nativeState.value.text,
                        profession = professionState.value.text,
                        imageUrl = imageUrl.value
                    )
                    navigateTo(navController, DestinationScreen.HomeScreen.route)
                    viewModel.onAddedProfile(nameState.value.text)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "CREATE PROFILE")
            }
        }
        if (viewModel.inProgress.value) {
            CommonProgressBar()
        }
    }
}

@Composable
fun ProfileImage(
    viewModel: KmViewModel
) :String{

    val uriState  = remember {
        mutableStateOf("")
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
             uriState.value = uri.toString()
        }
    }

    Box(
        modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
    ) {
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
                AsyncImage(
                    model = uriState.value,
                    contentDescription =null ,
                    modifier= Modifier.wrapContentSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = "Change Profile Picture")
        }
        if (viewModel.inProgress.value) {
            CommonProgressBar()
        }
    }
    return uriState.value
}

