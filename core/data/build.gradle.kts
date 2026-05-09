plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlinJsonSerialization)
}

android {
    namespace = "com.gfs.mobile.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }

    flavorDimensions.add("environment")
    productFlavors{
        create("production") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"http://192.168.100.3:3000/\"")
        }

        create("dev") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"http://192.168.116.193:3000/\"")
        }
    }
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.sqlite.ktx)
    kapt(libs.room.compiler)

    implementation(libs.datastore)

    implementation(libs.retrofit2)
    implementation(libs.converter.gson)
    implementation(libs.interceptor)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlin.serialization)
    
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.work)
    
    implementation(libs.timber.logging)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.kotlinx.coroutines.play.services)

    // Testing
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotest.assertions)
}
