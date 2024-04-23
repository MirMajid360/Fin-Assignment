import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("kotlin-android")
    id("realm-android")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")

}


android {
    namespace = "com.majid.mvvmtemplate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.majid.mvvmtemplate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        val date = Date()
        val formattedDate = SimpleDateFormat("HH-mm_dd-MM-yyyy").format(date)

// Set the archivesBaseName property
        setProperty("archivesBaseName", "MVVM-Template(" + versionName + ")_" + formattedDate)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //lifecycle mvvm for view model provider
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    //hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    // Allow references to generated code

    // Add DataStore dependencies
    implementation("androidx.datastore:datastore-preferences:1.1.0")
    implementation("androidx.datastore:datastore-core:1.1.0")
    implementation("androidx.datastore:datastore-preferences:1.1.0")


    // manage screen size
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation("com.intuit.sdp:sdp-android:1.1.0")


    // For Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")


    // FireBase
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation("androidx.work:work-runtime-ktx:2.9.0")


    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")
    //Firebace Auth
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    // Add the dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.code.gson:gson:2.10.1")


}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}