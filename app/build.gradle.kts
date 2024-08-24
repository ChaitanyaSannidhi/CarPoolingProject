plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.carpooling"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.carpooling"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // Firebase Analytics
    implementation("com.google.firebase:firebase-analytics")

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // Firestore Database
    implementation("com.google.firebase:firebase-firestore")

    // Google Play Services
    implementation("com.google.android.gms:play-services-auth:20.1.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database")

    // Fragment
    implementation("androidx.fragment:fragment:1.4.1")

    // Material Components
    implementation("com.google.android.material:material:1.8.0")

    // Firebase Storage
    implementation("com.google.firebase:firebase-storage")

    // Image loading libraries
    implementation("com.squareup.picasso:picasso:2.71828")

    // retrofit libraries
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.databinding:databinding-runtime:4.1.2")
}
