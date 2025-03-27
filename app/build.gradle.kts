import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}


val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}


android {
    namespace = "com.example.twisee"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.twisee"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String", "TWITCH_CLIENT_ID", "\"${localProperties["TWITCH_CLIENT_ID"]}\"")
        buildConfigField("String", "TWITCH_CLIENT_SECRET", "\"${localProperties["TWITCH_CLIENT_SECRET"]}\"")
        buildConfigField("String", "TWITCH_REDIRECT_URI", "\"${localProperties["TWITCH_REDIRECT_URI"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.browser:browser:1.5.0")
    implementation(libs.androidx.media3.common.ktx)
    kapt(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.coil.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
}