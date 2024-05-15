package com.ThandhBendhu.kodavamatrimony.ui

import android.net.Uri

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.ThandhBendhu.kodavamatrimony.data.ACCOUNTS
import com.ThandhBendhu.kodavamatrimony.data.Account
import com.ThandhBendhu.kodavamatrimony.data.BOOKMARK
import com.ThandhBendhu.kodavamatrimony.data.Bookmark
import com.ThandhBendhu.kodavamatrimony.data.CHATS
import com.ThandhBendhu.kodavamatrimony.data.ChatData
import com.ThandhBendhu.kodavamatrimony.data.ChatUser
import com.ThandhBendhu.kodavamatrimony.data.MESSAGE
import com.ThandhBendhu.kodavamatrimony.data.Message
import com.ThandhBendhu.kodavamatrimony.data.SignUpEvent
import com.ThandhBendhu.kodavamatrimony.data.USER_NODE
import com.ThandhBendhu.kodavamatrimony.data.UserData
import com.ThandhBendhu.kodavamatrimony.ui.Navigation.DestinationScreen
import com.ThandhBendhu.kodavamatrimony.ui.Utility.navigateTo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class KmViewModel @Inject constructor(
    val auth: FirebaseAuth,
    private var db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    var inProgress = mutableStateOf(false)
    var inProgressChat = mutableStateOf(false)
    var inProgressProfile = mutableStateOf(false)
    val eventMutableState = mutableStateOf<SignUpEvent<String?>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val creatingProfile = mutableStateOf(false)
    val profiles = mutableStateOf<List<UserData>>(listOf())
    val myProfiles = mutableStateOf<List<UserData>>(listOf())
    val myBookmarks = mutableStateOf<List<UserData>>(listOf())
    val myBookmarksData = mutableStateOf<List<Bookmark>>(listOf())
    val chats = mutableStateOf<List<ChatData>>(listOf())
    val chatMessages = mutableStateOf<List<Message>>(listOf())
    var currentChatMessageListener: ListenerRegistration? = null

    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }

    }

    fun populateMessages(
        chatId: String
    ) {
        inProgressChat.value = true
        currentChatMessageListener = db.collection(CHATS).document(chatId)
            .collection(MESSAGE).orderBy("sortTime", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error)
                }
                if (value != null) {
                    chatMessages.value = value.documents.mapNotNull {
                        it.toObject<Message>()
                    }

                    inProgressChat.value = false
                }


            }
    }

    fun depopulateMessages() {
        chatMessages.value = listOf()
        currentChatMessageListener = null
    }

    fun onAddChat(profileId: String) {

        if (profileId.isEmpty()) {
            handleException(customMessage = "invalid profile")
            return
        } else {

            db.collection(USER_NODE)
                .whereEqualTo("userId", profileId)
                .get()
                .addOnSuccessListener {

                    val chatPartner = it.toObjects<UserData>()[0]
                    val chatPartnerName = chatPartner.name
                    val chatPartnerAuth = chatPartner.authId

                    db.collection(ACCOUNTS)
                        .whereEqualTo("authId", auth.currentUser?.uid)
                        .get()
                        .addOnSuccessListener {
                            val myName = it.toObjects<UserData>()[0].name
                            val chatId = db.collection(CHATS).document().id
                            db.collection(CHATS)
                                .where(
                                    Filter.or(
                                        Filter.and(
                                            Filter.equalTo("user1.accId", auth.currentUser?.uid),
                                            Filter.equalTo("user2.accId", chatPartnerAuth)
                                        ),
                                        //chat already exists anta
                                        Filter.and(
                                            Filter.equalTo("user1.accId", chatPartnerAuth),
                                            Filter.equalTo("user2.accId", auth.currentUser?.uid)
                                        )
                                    )
                                ).get()
                                .addOnSuccessListener { chatExist ->
                                    if (chatExist.isEmpty) {
                                        val chat =
                                            ChatData(
                                                chatId = chatId,
                                                user1 = ChatUser(
                                                    accId = auth.currentUser?.uid,
                                                    name = myName ?: "Anonymous"
                                                ),
                                                user2 = ChatUser(
                                                    accId = chatPartnerAuth,
                                                    name = chatPartnerName ?: "Name Not Found"
                                                )
                                            )
                                        db.collection(CHATS).document(chatId).set(chat)
                                    }
                                }
                        }
                        .addOnFailureListener {
                            handleException(it)
                        }
                }


        }
    }

    fun populateChat(

    ) {
        inProgressChat.value = true
        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.accId", auth.currentUser?.uid),
                Filter.equalTo("user2.accId", auth.currentUser?.uid)
            )

        ).addSnapshotListener { value, error ->
            if (value != null) {
                chats.value = value.documents.mapNotNull {
                    it.toObject<ChatData>()
                }
                inProgressChat.value = false
            }

        }

    }

    fun onSendReply(
        chatId: String,
        msg: String
    ) {
        val time = Calendar.getInstance().time.toString()
        val sortTime = Calendar.getInstance().time.time.toString()

        val thisMessage = Message(
            sendBy = auth.currentUser?.uid,
            message = msg,
            timeStamp = time,
            sortTime = sortTime
        )
        db.collection(CHATS).document(chatId).collection(MESSAGE).document().set(thisMessage)
    }

    fun login(
        email: String,
        password: String,
        navController: NavController

    ) = CoroutineScope(Dispatchers.IO).launch{
        if (email.isEmpty() || password.isEmpty()) {
            handleException(customMessage = "Please fill all the fields")

        } else {
            inProgress.value = true
            auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    handleException( customMessage = "Login failed")
                }
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        signIn.value = true
                        inProgress.value = false
                        auth.currentUser?.uid?.let {
                            getUserData(it)
                        }
                        afterLogin(navController)
                    } else {
                        handleException(it.exception, customMessage = "Login failed")
                    }

                }
                .await()
        }
    }
private fun afterLogin(
    navController: NavController
)= CoroutineScope(Dispatchers.Main).launch{
    delay(500)
    navigateTo(navController,DestinationScreen.HomeScreen.route)
}

    fun logout() {
        signIn.value = false
        userData.value = null
        depopulateMessages()
        auth.signOut()
        currentChatMessageListener = null
    }
    fun signUp1(
        name: String,
        email: String,
        password: String,
        navController: NavController
    ) = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true
        if (email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "Please Fill All The Fields")
        }

        auth.createUserWithEmailAndPassword(email, password)

            .addOnFailureListener {
                handleException(it)
            }
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    createAccount(name, email, password)
                    signIn.value = true
                    inProgress.value = false
                    navigateTo(navController, DestinationScreen.HomeScreen.route)
                } else {
                    handleException(customMessage = " SignUp error")
                }
            }


    }

    fun createAccount(
        name: String,
        email: String,
        pwd: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        val acc = Account(
            name = name,
            emailId = email,
            pwd = pwd,
            authId = auth.currentUser?.uid

        )
        db.collection(ACCOUNTS)
            .document()
            .set(acc)

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
        gender: String? = null,
        education: String? = null,
        timeOfBirth: String? = null,
        settledPlace: String? = null,
        siblings: String? = null,
        property: String? = null,
        native: String? = null,
        height: String? = null,
        maritalStatus: String? = null,
        profession: String? = null,

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
            gender = gender ?: userData.value?.gender,
            timeOfBirth = timeOfBirth ?: userData.value?.timeOfBirth,
            location = settledPlace ?: userData.value?.location,
            education = education ?: userData.value?.education,
            property = property ?: userData.value?.property,
            siblings = siblings ?: userData.value?.siblings,
            nativePlace = native ?: userData.value?.nativePlace,
            height = height ?: userData.value?.height,
            maritalStatus = maritalStatus ?: userData.value?.maritalStatus,
            profession = profession ?: userData.value?.profession
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
                    populateProfiles()
                    populateChat()
                    onShowBookmark()
                    inProgress.value = false

                }
            }
    }

    fun getMyProfilesData() {

        inProgress.value = true
        if (auth.currentUser?.uid != null) {
            db.collection(USER_NODE)
                .document(auth.currentUser?.uid!!)
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

    fun handleException(
        exception: Exception? = null,
        customMessage: String = ""
    ) {

        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage
        val message =
            if (customMessage.isEmpty())
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
        db.collection(USER_NODE)
            .whereEqualTo("userId", bookmarkId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    myBookmarks.value = value.documents.mapNotNull {
                        it.toObject<UserData>()
                    }

                    val bmk = Bookmark(
                        auth.currentUser?.uid,
                        myBookmarks.value[0]
                    )
                    db.collection(BOOKMARK).document().set(bmk)
                }
            }
        inProgress.value = false
    }

    fun onDeleteProfile(
        profileId: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true
        db.collection(USER_NODE).where(
            Filter.and(

                Filter.equalTo("authId", auth.currentUser?.uid),
                Filter.equalTo("userId", profileId)
            )
        ).get()
            .addOnSuccessListener { value ->
                value.documents.mapNotNull {
                    it.reference.delete()
                }
            }
            .await()
        inProgress.value = false
    }

    fun onShowBookmark() {
        inProgressProfile.value = true
        db.collection(BOOKMARK)
            .whereEqualTo("authenticationId", auth.currentUser?.uid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    myBookmarksData.value = value.documents.mapNotNull {
                        it.toObject<Bookmark>()
                    }
                    inProgressProfile.value = false
                }
            }

    }

    fun onCreateProfile() {
        inProgressProfile.value = true
        db.collection(USER_NODE)
            .whereEqualTo("authId", auth.currentUser?.uid)
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


}

