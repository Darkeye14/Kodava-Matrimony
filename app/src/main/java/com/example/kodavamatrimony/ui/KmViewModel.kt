package com.example.kodavamatrimony.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kodavamatrimony.data.SignUpEvent
import com.example.kodavamatrimony.data.UserData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class KmViewModel @Inject constructor(
   private val auth : FirebaseAuth
) : ViewModel() {



init {

}
    var inProgress = mutableStateOf(false)
    val eventMutableState = mutableStateOf<SignUpEvent<String?>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
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
                createOrUpdateProfile(name,number)
            }
                else{
                    handleException(it.exception,"SignUp failed")
            }
        }
    }

    private fun createOrUpdateProfile(
        name: String?=null,
        number: String?=null,
        imageUrl :String?=null
    ) {
        val uid = auth.currentUser?.uid

        val userData = UserData(
            userId = uid,
            name = name ?:userData.value?.name ,
            number = number?:userData.value?.number,
            imageUrl = imageUrl?:userData.value?.imageUrl
        )
        uid.let {
            inProgress.value = true
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

}


