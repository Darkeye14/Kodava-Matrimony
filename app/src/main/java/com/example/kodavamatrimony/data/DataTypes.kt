package com.example.kodavamatrimony.data

data class UserData(
    var userId : String?= null,
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
){
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "familyName" to familyName,
        "number" to number,
        "fathersName" to fathersName,
        "mothersName" to mothersName,
        "age" to age,
        "description" to description,
        "requirement" to requirement,
        "imageUrl" to imageUrl

    )
}
data class UserAuthData(
    var userId : String?= "",
    var email : String?= "",
    var password : String?= "",
){
    fun toMap() = mapOf(
        "userId" to userId,
        "email" to email,
        "password" to password
    )
}

data class ChatProfileData(
    val profileId : String? = "",
    val user1 : UserProfile = UserProfile(),
    val user2 : UserProfile = UserProfile()
)

data class UserProfile(
    val userId: String? = "",
    val name: String? =null,
    val imageUrl: String? =null,
    val age: String? =null,
    val gender: String? =null

)
data class Bookmark(
    val userId: String? = null,
    val bmkId: String? =null,

)