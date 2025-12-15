plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // enable KAPT
}

android {
    namespace = "com.example.team_26_project"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.team_26_project"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ROOM
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //okHTTP for talking to the internet
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //junit
    //Unit test
    testImplementation("junit:junit:4.13.2")

    // AndroidX Test Core
    testImplementation("androidx.test:core:1.5.0")

    // Room testing
    testImplementation("androidx.room:room-testing:2.6.1")

    // Coroutines testing
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Mockito
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    //OkHttp
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")


    //mockito
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("junit:junit:4.13.2")

    //esspreso
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")

}