package com.example.kodavamatrimony.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.kodavamatrimony.data.AUTH
import com.example.kodavamatrimony.data.SignUpEvent
import com.example.kodavamatrimony.data.ChatProfileData
import com.example.kodavamatrimony.data.ALL_PROFILES
import com.example.kodavamatrimony.data.BOOKMARK
import com.example.kodavamatrimony.data.Bookmark
import com.example.kodavamatrimony.data.USER_NODE
import com.example.kodavamatrimony.data.UserAuthData
import com.example.kodavamatrimony.data.UserData
import com.example.kodavamatrimony.data.UserProfile
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.navigateTo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
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
    val userAuthData = mutableStateOf<UserAuthData?>(null)
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
                createOrUpdateAuth(email, password)
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

    fun createOrUpdateAuth(
        email :String?= null,
        password : String?= null,

    ){
        val uid = auth.currentUser?.uid
        val userAuthData = UserAuthData(
            userId = uid,
            email = email ?:userAuthData.value?.email ,
            password = password?:userAuthData.value?.password,

        )
        uid?.let {
            inProgress.value = true
            db.collection(AUTH)
                .document(uid)
                .get()
                .addOnSuccessListener {
                    if(it.exists()){
                        //update
                    }
                    else{
                        db.collection(AUTH)
                            .add(userAuthData)
                        getUserData(uid)
                        inProgress.value = false
                        creatingProfile.value = false
                    }
                }
                .addOnFailureListener{
                    handleException(it,"Cannot Add User")
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
        imageUrl: String?=null,
        gender : String?= null
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
            imageUrl = imageUrl?:userData.value?.imageUrl,
            gender = gender?:userData.value?.gender
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
                    populateProfiles()
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

    fun onAddedProfile(name: String) {
        if(name.isEmpty() ){
            handleException(customMessage = "Name Error")
        }else{
            db.collection(USER_NODE)
                .whereEqualTo("name",name)
                .get()
                .addOnSuccessListener {
                    if(it.isEmpty){
                        handleException(customMessage = "profile not found")
                    }else{
                       val chatPartner = it.toObjects<UserData>()[0]
                        val id = db.collection(ALL_PROFILES).document().id
                        val profile = ChatProfileData(
                            profileId = id,
                            UserProfile(
                                userData.value?.userId,
                                userData.value?.name,
                                userData.value?.imageUrl,
                                userData.value?.age,
                                userData.value?.gender
                            ),
                            UserProfile(
                                chatPartner.userId,
                                chatPartner.name,
                                chatPartner.imageUrl,
                                chatPartner.age,
                                chatPartner.gender,

                            )
                        )
                        db.collection(ALL_PROFILES).document(id)
                            .set(profile)
                    }
                }
                .addOnFailureListener {
                    handleException(it)
                }
        }
    }

    fun populateProfiles(

    ){
        inProgressProfile.value = true
        db.collection(ALL_PROFILES).where(
            Filter.or(
                Filter.equalTo("user1.userId",userData.value?.userId),
                Filter.equalTo("user2.userId",userData.value?.userId)

            )
        ).addSnapshotListener{value,error->
// smthin here
            if(value !=null){
                profiles.value = value.documents.mapNotNull {
                    it.toObject<ChatProfileData>()
                }
                inProgressProfile.value = false
            }
        }
    }

    fun onBookmark(
        bookmarkId :String
    ){
       val  bookmark = Bookmark(
           userAuthData.value?.userId,
           bookmarkId
       )
        db.collection(AUTH).document(bookmarkId).collection(BOOKMARK).document().set(bookmark)
    }


}


