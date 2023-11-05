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
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation(Dependencies.junit)
    testImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espresso)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.testJunit4)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation(Dependencies.tooling)
    debugImplementation(Dependencies.testManifest)

    implementation(Dependencies.navigationCompose)

    implementation(Dependencies.daggerHilt)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
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

}
 kapt {
    correctErrorTypes = true
}