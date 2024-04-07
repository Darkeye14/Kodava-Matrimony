package com.example.kodavamatrimony.ui

import android.net.Uri
import android.util.Log

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.kodavamatrimony.data.BOOKMARK
import com.example.kodavamatrimony.data.Bookmark
import com.example.kodavamatrimony.data.SignUpEvent
import com.example.kodavamatrimony.data.USER_NODE
import com.example.kodavamatrimony.data.UserData
import com.example.kodavamatrimony.ui.Navigation.DestinationScreen
import com.example.kodavamatrimony.ui.Utility.navigateTo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class KmViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private var db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {


    var inProgress = mutableStateOf(false)
    var inProgressProfile = mutableStateOf(false)
    val eventMutableState = mutableStateOf<SignUpEvent<String?>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val bmkData = mutableStateOf<UserData?>(null)
    val creatingProfile = mutableStateOf(false)
    val profiles = mutableStateOf<List<UserData>>(listOf())
    val myProfiles = mutableStateOf<List<UserData>>(listOf())
    val myBookmarks = mutableStateOf<List<UserData>>(listOf())
    val myBookmarksData = mutableStateOf<List<UserData>>(listOf())
    init {
        populateProfiles()
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }

    }

    fun signUp(
        email: String,
        password: String,
        navController: NavController
    ) {
        inProgress.value = true
        if (email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "Please Fill All The Fields")
            return
        }
        inProgress.value = true
        db.collection(USER_NODE).whereEqualTo("email", email)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            ////
                            navigateTo(navController, DestinationScreen.HomeScreen.route)
                        }
                    //Failure Listener
                } else {
                    handleException(customMessage = "email already exist")
                    inProgress.value = false
                }
            }
            .addOnFailureListener {
                handleException(it)
                return@addOnFailureListener
            }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {                    //gyanpg@gmail.com   gyan12345

                ////
                navigateTo(navController, DestinationScreen.HomeScreen.route)
                if (it.isSuccessful) {
                    signIn.value = true
                    inProgress.value = false
// do another fun for auth too
//just navigate
                    // createOrUpdateProfile(name = name,number = number)
                } else {
                    handleException(it.exception, "SignUp failed")
                }
            }
    }


    fun createOrUpdateProfile(
        name: String? = null,
        familyName: String? = null,
        number: String? = null,
        fathersName: String? = null,
        mothersName: String? = null,
        age: String? = null,
        description: String? = null,
        requirement: String? = null,
        imageUrl: String? = null,
        gender: String? = null
    ) {
        val uid = UUID.randomUUID().toString()
        val userData = UserData(
            userId = uid,
            authId = auth.currentUser?.uid,
            name = name ?: userData.value?.name,
            familyName = familyName ?: userData.value?.name,
            number = number ?: userData.value?.number,
            fathersName = fathersName ?: userData.value?.number,
            mothersName = mothersName ?: userData.value?.number,
            age = age ?: userData.value?.number,
            description = description ?: userData.value?.number,
            requirement = requirement ?: userData.value?.number,
            imageUrl = imageUrl ?: userData.value?.imageUrl,
            gender = gender ?: userData.value?.gender
        )
        uid.let {
            inProgress.value = true
            db.collection(USER_NODE)
                .document(uid)
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        //update
                    } else {
                        db.collection(USER_NODE)
                            .add(userData)
                        getUserData(uid)
                        getMyProfilesData()
                        inProgress.value = false
                        creatingProfile.value = false
                    }
                }
                .addOnFailureListener {
                    handleException(it, "Cannot Retrieve User")
                }
        }

    }

    fun initSearch() {
        inProgressProfile.value = true
        db.collection(USER_NODE)
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    profiles.value = it.documents.mapNotNull {
                        it.toObject<UserData>()
                    }
                    inProgressProfile.value = false
                }
            }


    }

    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USER_NODE)
            .document(uid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    val user = value.toObject<UserData>()
                    userData.value = user
                    inProgress.value = false
                    populateProfiles()
                }
            }
    }

    fun getMyProfilesData() {
        val uid = auth.currentUser?.uid
        inProgress.value = true
        if (uid != null) {
            db.collection(USER_NODE)
                .document(uid)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        handleException(error, "cannot retrieve user")
                    }
                    if (value != null) {
                        val user = value.toObject<UserData>()
                        userData.value = user
                        inProgress.value = false
                        onCreateProfile()
                    }
                }
        }
    }

    fun getMyBookmarksData() {
        val uid = auth.currentUser?.uid
        inProgress.value = true
        if (uid != null) {
            db.collection(BOOKMARK)
                .document(uid)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        handleException(error, "cannot retrieve user")
                    }
                    if (value != null) {
                        val user = value.toObject<UserData>()
                        bmkData.value = user
                        inProgress.value = false
                        onShowBookmark()
                    }
                }
        }
    }

    fun login(
        email: String,
        password: String,
        navController: NavController

    ) {
        if (email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "Please fill all the fields")
            return
        } else {
            inProgress.value = true
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        signIn.value = true
                        inProgress.value = false
                        auth.currentUser?.uid?.let {
                            getUserData(it)
                        }
                        navigateTo(navController, DestinationScreen.HomeScreen.route)
                    } else {
                        handleException(it.exception, customMessage = "Login failed")
                    }
                }
        }
    }


    fun handleException(
        exception: Exception? = null,
        customMessage: String = ""
    ) {
        Log.e("KmApp", "signUp Exception", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage
        val message =
            if (customMessage.isNullOrEmpty())
                errorMsg
            else
                customMessage

        eventMutableState.value = SignUpEvent(message)
        inProgress.value = false
    }

    fun uploadProfileImage(
        uri: Uri
    ) {
        uploadImage(uri) {
            createOrUpdateProfile(imageUrl = it.toString())
        }
    }

    fun uploadImage(
        uri: Uri,
        onSuccess: (Uri) -> Unit
    ) {
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
            .addOnFailureListener {
                handleException(it)
            }
    }

    fun onAddedProfile(name: String) {
        if (name.isEmpty()) {
            handleException(customMessage = "Name Error")
        } else {
            db.collection(USER_NODE)
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        handleException(customMessage = "profile not found")

                    }
                }
                .addOnFailureListener {
                    handleException(it)
                }
        }
    }

    fun populateProfiles(

    ) {
        inProgressProfile.value = true
        db.collection(USER_NODE)
            .addSnapshotListener { value, error ->
// smthin here
                if (value != null) {
                    profiles.value = value.documents.mapNotNull {
                        it.toObject<UserData>()
                    }
                    inProgressProfile.value = false
                }
            }
    }

    fun onBookmark(
        bookmarkId: String
    ) {
        val currentAuthId = auth.currentUser?.uid
        db.collection(USER_NODE)
            .whereEqualTo("userId", bookmarkId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    myBookmarksData.value = value.documents.mapNotNull {
                        it.toObject<UserData>()
                    }
                    inProgress.value = false
                    val bmk = Bookmark(
                        currentAuthId,
                        myBookmarksData.value[0]
                    )
                    db.collection(BOOKMARK).document().set(bmk)
                }
            }

    }

    fun onShowBookmark() {
        val currentAuthId = auth.currentUser?.uid
        inProgressProfile.value = true
        db.collection(BOOKMARK)
            .whereEqualTo("authId", currentAuthId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    myBookmarks.value = value.documents.mapNotNull {
                        it.toObject<UserData>()
                    }
                    inProgressProfile.value = false
                }
            }

    }

    fun onCreateProfile() {
        val currentAuthId = auth.currentUser?.uid
        inProgressProfile.value = true
        db.collection(USER_NODE)
            .whereEqualTo("authId", currentAuthId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    myProfiles.value = value.documents.mapNotNull {
                        it.toObject<UserData>()
                    }
                    inProgressProfile.value = false
                }
            }
    }

    fun onDelete(
        profileId: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true
        val currentAuthId = auth.currentUser?.uid
        val toBeDeleted = db.collection(USER_NODE).where(
            Filter.and(

                Filter.equalTo("authId", currentAuthId),
                Filter.equalTo("userId", profileId)

            )
        ).get().await()

        if(toBeDeleted.documents.isNotEmpty()){

            for(document in toBeDeleted){
                try {
                    db.collection(USER_NODE).document(document.id).delete().await()
                }catch (e: Exception) {
                    handleException(e)
                    }
                inProgress.value=false
            }
        }
        inProgress.value = false

    }

}


