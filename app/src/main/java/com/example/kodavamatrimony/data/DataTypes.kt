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

data class ChatProfileData(
    val profileId : String? = "",
    val user1 : CurrentUserProfile = CurrentUserProfile()
)

data class CurrentUserProfile(
    val userId: String? = "",
    val name: String? ="",
    val imageUrl: String? =""

)