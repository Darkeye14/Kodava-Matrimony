package com.ThandhBendhu.kodavamatrimony.data

open class SignUpEvent<out T>(
    val content : T
) {
    var hasBeenHandled = false
    fun getContentOrNull():T?{
        return if(hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }
}