package com.example.kodavamatrimony.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.kodavamatrimony.data.SignUpEvent
import com.example.kodavamatrimony.data.ChatProfileData
import com.example.kodavamatrimony.data.USER_NODE
import com.example.kodavamatrimony.data.UserData
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.navigateTo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class KmViewModel @Inject constructor(
   private val auth : FirebaseAuth,
    private var db : FirebaseFirestore,
    private val storage : FirebaseStorage
) : ViewModel() {




    var inProgress = mutableStateOf(false)
    var inProgressProfile = mutableStateOf(false)
    val eventMutableState = mutableStateOf<SignUpEvent<String?>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val creatingProfile = mutableStateOf(false)
    val profiles = mutableStateOf<List<ChatProfileData>>(listOf())

    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }
    fun signUp(
        name :String,
        number :String,
        email :String,
        password : String,
        navController: NavController
    ){
        inProgress.value = true
        if(email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please Fill All The Fields")
            return
        }
        inProgress.value = true
        db.collection(USER_NODE).whereEqualTo("email",email)
            .get()
            .addOnSuccessListener {
                if(it.isEmpty){
                    auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener {
    ////
                            navigateTo(navController,DestinationScreen.HomeScreen.route)
                    }
                }else{
                    handleException(customMessage = "email already exist")
                        inProgress.value=false
                }
            }
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
    ////
                navigateTo(navController,DestinationScreen.HomeScreen.route)
            if(it.isSuccessful){
                signIn.value =true
                inProgress.value = false
// do another fun for auth too
//just navigate
               // createOrUpdateProfile(name = name,number = number)
            }
                else{
                    handleException(it.exception,"SignUp failed")
            }
        }
    }


    fun createOrUpdateProfile(
        name :String?= null,
        familyName : String?= null,
        number :String?= null,
        fathersName :String?= null,
        mothersName :String?= null,
        age: String?= null,
        description :String?= null,
        requirement :String?= null,
        imageUrl: String?=null
    ){
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?:userData.value?.name ,
            familyName = familyName?:userData.value?.name,
            number = number?:userData.value?.number,
            fathersName = fathersName?:userData.value?.number,
            mothersName = mothersName?:userData.value?.number,
            age = age?:userData.value?.number,
            description = description?:userData.value?.number,
            requirement = requirement?:userData.value?.number,
            imageUrl = imageUrl?:userData.value?.imageUrl
        )
        uid?.let {
            inProgress.value = true
            db.collection(USER_NODE)
                .document(uid)
                .get()
                .addOnSuccessListener {
                    if(it.exists()){
  //update
                    }
                    else{
                        db.collection(USER_NODE)
                            .add(userData)
                        getUserData(uid)
                        inProgress.value = false
                        creatingProfile.value = false
                    }
                }
                .addOnFailureListener{
                    handleException(it,"Cannot Retrieve User")
                }
        }

    }

    private fun getUserData(uid :String) {
        inProgress.value=true
        db.collection(USER_NODE)
            .document(uid)
            .addSnapshotListener { value, error ->
                if(error !=null){
                    handleException(error,"cannot retrieve user")
                }
                if(value!=null){
                    val user = value.toObject<UserData>()
                    userData.value = user
                    inProgress.value = false
                }
            }
    }

    fun login(
        email:String,
        password: String,
        navController: NavController

    ){
        if(email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please fill all the fields")
            return
        }else{
            inProgress.value = true
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        signIn.value = true
                        inProgress.value = false
                        auth.currentUser?.uid?.let{
                            getUserData(it)
                        }
                        navigateTo(navController,DestinationScreen.HomeScreen.route)
                    }else{
                        handleException(it.exception,customMessage = "Login failed")
                    }
                }
        }
    }



    fun handleException(
        exception: Exception?=null,
        customMessage : String=""
    ){
        Log.e("KmApp","signUp Exception",exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage
        val message =
            if (customMessage.isNullOrEmpty())
            errorMsg
        else
            customMessage

        eventMutableState.value  = SignUpEvent(message)
        inProgress.value = false
    }

    fun uploadProfileImage(
        uri : Uri
    ){
        uploadImage(uri){
            createOrUpdateProfile(imageUrl = it.toString())
        }
    }

    fun uploadImage(
        uri : Uri,
        onSuccess : (Uri) ->Unit
    ){
        inProgress.value = true
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef
            .putFile(uri)
        uploadTask.addOnSuccessListener {
                val result = it.metadata
                    ?.reference
                    ?.downloadUrl
                result?.addOnSuccessListener(onSuccess)
                inProgress.value = false
            }
                .addOnFailureListener{
                    handleException(it)
                }
    }

    fun onSaveProfile(it: String) {

    }

}


