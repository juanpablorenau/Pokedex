plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_BASE_URL", "\"https://pokeapi.co/api/v2/\"")
        }

        release {
            buildConfigField("String", "API_BASE_URL", "\"https://pokeapi.co/api/v2/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
}

dependencies {

    //Module
    implementation(project(":domain"))

    // Core
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.retrofit2.converter.gson)

    // OkHttp
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    // Testing
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter.api)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.robolectric)
}
