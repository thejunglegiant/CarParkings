plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.thejunglegiant.carparkings"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thejunglegiant.carparkings"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // Socket
    val socketIoVersion = "2.1.0"
    implementation("io.socket:socket.io-client:$socketIoVersion")

    // Network
    val retrofitVersion = "2.9.0"
    val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"

    val okHttpVersion = "4.11.0"
    val okhttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"

    val networkDependencies = listOf(
        retrofit,
        retrofitGsonConverter,
        okhttp,
        okhttpLogging,
    )
    networkDependencies.forEach(::implementation)

    // Koin
    val koinVersion = "3.4.0"
    val koinComposeVersion = "3.4.4"

    val koinCore = "io.insert-koin:koin-core:$koinVersion"
    val koin = "io.insert-koin:koin-android:$koinVersion"
//        const val koinViewModel = "io.insert-koin:koin-androidx-viewmodel:$koinVersion"
    val koinCompat = "io.insert-koin:koin-android-compat:$koinVersion"
    val koinCompose = "io.insert-koin:koin-androidx-compose:$koinComposeVersion"

    val koinDependencies = listOf(
        koin,
        koinCore,
        koinCompat,
        koinCompose,
    )
    koinDependencies.forEach(::implementation)

    // Appyx
    val appyx = "com.bumble.appyx:core:1.4.0"
    implementation(appyx)

    // Maps
    implementation("com.google.maps.android:maps-compose:4.3.0")
}