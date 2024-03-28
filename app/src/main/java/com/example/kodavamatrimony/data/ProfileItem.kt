package com.example.kodavamatrimony.data

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileItem(
    var id: String = "",
     val profilePicUrl: String,
     val familyName: String ="",
    val fatherName: String ="",
    val mothersName: String ="",
    val name : String = "",
    val discription : String? = "",
    val requirements : String?= "",
    val phoneNumber :String? = "",
    val age : Int = 0
)
