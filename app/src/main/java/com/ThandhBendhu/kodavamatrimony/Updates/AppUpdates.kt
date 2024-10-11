package com.ThandhBendhu.kodavamatrimony.Updates

import android.content.IntentSender
import com.ThandhBendhu.kodavamatrimony.MainActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed

fun appUpdates(
    thisMessage : MainActivity
) {

     lateinit var appUpdateManager: AppUpdateManager
     val updateType = AppUpdateType.IMMEDIATE
    val IMMEDIATE_UPDATE_REQUEST_CODE = 123
    try {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        thisMessage,
                        IMMEDIATE_UPDATE_REQUEST_CODE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    // Handle the exception
                }
            }
        }
    }catch (e: Exception){
        e.printStackTrace()
    }
}