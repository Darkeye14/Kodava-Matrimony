package com.example.kodavamatrimony.di

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object MatrimonyModule {
//
//    @Provides
//    @Singleton
//    fun providesFirebaseFireStoreDataBase():FirebaseFirestore{
//        return FirebaseFirestore.getInstance()
//    }
//}