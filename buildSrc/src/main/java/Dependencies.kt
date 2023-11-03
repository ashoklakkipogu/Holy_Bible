object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val composeUi by lazy { "androidx.compose.ui:ui" }
    val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3:${Versions.composeMaterial3}" }
    val composeMaterial3Window by lazy { "androidx.compose.material3:material3-window-size-class:${Versions.composeMaterial3}" }
    val navigationRuntimeKtx by lazy { "androidx.navigation:navigation-runtime-ktx:${Versions.navigationRuntimeKtx}" }
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val androidxJunit by lazy { "androidx.test.ext:junit:${Versions.androidxJunit}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val testJunit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
    val tooling by lazy { "androidx.compose.ui:ui-tooling" }
    val testManifest by lazy { "androidx.compose.ui:ui-test-manifest" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }
    val daggerHilt by lazy { "com.google.dagger:hilt-android:${Versions.dagger}" }
    val daggerHiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.dagger}" }
//    val hiltViewModel by lazy { "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModel}" }
//    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltViewModel}" }

    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltViewModel}" }
    val constraintlayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesCore}" }
    val viewmodelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodel}" }
    val livedataKtx by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleViewmodel}" }
    val lifecycleCompiler by lazy { "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleViewmodel}" }
    val loggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}" }
    val splashscreen by lazy { "androidx.core:core-splashscreen:${Versions.splashscreen}" }

}