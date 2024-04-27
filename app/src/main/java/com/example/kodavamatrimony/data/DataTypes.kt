package com.example.kodavamatrimony.data

data class UserData(
    var userId : String?= null,
    var authId : String?= null,
    var name : String?= null,
    var familyName : String?= null,
    var number : String?= null,
    var fathersName :String?= null,
    var mothersName :String?= null,
    var age :String? = null,
    var description :String?= null,
    var requirement :String?= null,
    var imageUrl : String?= null,
    var gender : String?=null
)
data class Bookmark(
    val authenticationId: String? = null,
    val data: UserData? =null,

)
data class Account(
    val name: String,
    val emailId : String,
    val pwd : String,
    val authId: String?
)
data class ChatData(
    val chatId : String,
    val user1 : ChatUser = ChatUser(),
    val user2 : ChatUser = ChatUser()
)

data class ChatUser(
    val accId : String ?="",
    val name : String=""
)