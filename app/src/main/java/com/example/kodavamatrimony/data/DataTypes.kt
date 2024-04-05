package com.example.kodavamatrimony.data

data class UserData(
    var userId : String?= "",
    var name : String?= "",
    var familyName : String?= "",
    var number : String?= "",
    var fathersName :String?= "",
    var mothersName :String?= "",
    var age :String? = "",
    var description :String?= "",
    var requirement :String?= "",
    var imageUrl : String?= "",
    var gender : String?=""
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
    val name: String? ="",
    val imageUrl: String? ="",
    val age: String? ="",
    val gender: String? =""

)