plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.ashok.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ashok.myapplication"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
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
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycle)
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterial3)
    implementation(Dependencies.composeMaterial3Window)
    implementation(Dependencies.navigationRuntimeKtx)
    testImplementation(Dependencies.junit)
    testImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espresso)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.testJunit4)
    androidTestImplementation(platform(Dependencies.composeBom))
    debugImplementation(Dependencies.tooling)
    debugImplementation(Dependencies.testManifest)

    implementation(Dependencies.navigationCompose)

    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerHiltCompiler)

    implementation(Dependencies.hiltNavigationCompose)

    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGson)

    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)


    implementation(Dependencies.viewmodelKtx)
    implementation(Dependencies.livedataKtx)
    kapt(Dependencies.lifecycleCompiler)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.splashscreen)

    implementation("io.coil-kt:coil-compose:2.5.0")
    // Room SQLite for data persistence
    val room_version = "2.6.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation("dev.shreyaspatil:capturable:1.0.3")




}
 kapt {
    correctErrorTypes = true
}