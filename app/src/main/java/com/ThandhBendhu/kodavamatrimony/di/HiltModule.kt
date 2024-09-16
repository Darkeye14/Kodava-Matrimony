package com.ThandhBendhu.kodavamatrimony.di

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun providesFirebaseAuthentication():FirebaseAuth = Firebase.auth


    @Provides
    @Singleton
    fun providesFirebaseFireStoreDataBase():FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesStorage():FirebaseStorage = Firebase.storage

//    @Provides
//    fun providesMessaging(): FirebaseMessaging = Firebase.messaging

}
