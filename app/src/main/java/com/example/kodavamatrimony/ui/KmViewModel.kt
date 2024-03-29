package com.example.kodavamatrimony.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kodavamatrimony.data.PROFILES
import com.example.kodavamatrimony.data.SignUpEvent
import com.example.kodavamatrimony.data.USER_NODE
import com.example.kodavamatrimony.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KmViewModel @Inject constructor(
   private val auth : FirebaseAuth,
    private var db : FirebaseFirestore
) : ViewModel() {



init {

}
    var inProgress = mutableStateOf(false)
    val eventMutableState = mutableStateOf<SignUpEvent<String?>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val creatingProfile = mutableStateOf(false)
    fun signUp(
        name :String,
        number :String,
        email :String,
        password : String
    ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                inProgress.value = true
            if(it.isSuccessful){
                signIn.value =true
//just navigate
               // createOrUpdateProfile(name,number)
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
        if(!creatingProfile.value) {
            inProgress.value = true
            db.collection(PROFILES)
                .document()
    //update            .update(userData)
//                .addOnSuccessListener {
//
//                }

        }
        else{
            db.collection(USER_NODE)
                .document()
                .set(userData, SetOptions.merge())
                .addOnFailureListener{
                    handleException(it,"Cannot Add User")
                }
            getUserData()
            inProgress.value = false
            creatingProfile.value = false
        }
    }

    private fun getUserData() {

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

}


