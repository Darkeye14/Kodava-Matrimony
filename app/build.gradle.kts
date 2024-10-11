plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ThandhBendhu.kodavamatrimony"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ThandhBendhu.kodavamatrimony"
        minSdk = 24
        targetSdk = 34
        versionCode = 10
        versionName = "10.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.android)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.messaging.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // ViewModel Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")

    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    ksp ("com.google.dagger:hilt-compiler:2.51.1")

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.51")
    kspAndroidTest ("com.google.dagger:hilt-compiler:2.51")

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.51.1")
    kspTest ("com.google.dagger:hilt-compiler:2.51.1")

    //coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    //In app google play update
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")

}