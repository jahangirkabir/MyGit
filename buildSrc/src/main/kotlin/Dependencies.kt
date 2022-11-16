object Kotlin {
    const val standardLibrary = "1.7.20"
    const val coroutines = "1.3.9"
    const val safeArgsGradlePlugin = "2.5.3"
}

object AndroidSdk {
    const val min = 26
    const val compile = 33
    const val target = compile
}

object AndroidClient {
    const val appId = "com.jahanbabu.mygit"
    const val versionCode = 1
    const val versionName = "1.0"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "7.3.1"
        const val gradleVersion = "7.4"
        const val hilt = "2.44.2"
        const val secretsGradlePlugin = "2.0.1"
        const val jacoco = "0.8.8"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.standardLibrary}"
    const val safeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Kotlin.safeArgsGradlePlugin}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val secretsGradlePlugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secretsGradlePlugin}"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val androidHilt = "com.google.dagger.hilt.android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val safeargs = "androidx.navigation.safeargs"
    const val secretsGradle = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    const val jacoco = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
}

object ScriptPlugins {
    const val infrastructure = "scripts.infrastructure"
    const val variants = "scripts.variants"
    const val quality = "scripts.quality"
    const val compilation = "scripts.compilation"
}

object Libraries {
    private object Versions {
        const val hilt = BuildPlugins.Versions.hilt
        const val hiltX = "1.0.0"
        const val hiltNavigationFragment = "1.0.0"
        const val appCompat = "1.5.1"
        const val constraintLayout = "2.1.4"
        const val recyclerView = "1.2.1"
        const val cardView = "1.0.0"
        const val material = "1.1.0"
        const val fragment = "1.5.4"
        const val navigation = "2.5.3"
        const val lifecycle = "2.5.1"
        const val lifecycleExtensions = "2.2.0"
        const val annotations = "1.5.0"
        const val dataStore = "1.0.0"
        const val paging = "3.1.1"
        const val ktx = "1.9.0"
        const val glide = "4.11.0"
        const val glideTransformations = "4.3.0"
        const val retrofit = "2.9.0"
        const val okHttpLoggingInterceptor = "4.9.0"
        const val moshi = "1.14.0"
        const val moshiKotlin = "1.12.0"
        const val moshiConverter = "2.9.0"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Kotlin.standardLibrary}"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Kotlin.coroutines}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Kotlin.coroutines}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val androidAnnotations = "androidx.annotation:annotation:${Versions.annotations}"
    const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    const val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideTransformations = "jp.wasabeef:glide-transformations:${Versions.glideTransformations}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltNavigationFragment = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigationFragment}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltXCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltX}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.moshiConverter}"
    const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiKotlin}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13.1"
        const val mockk = "1.13.2"
        const val arcCore = "2.1.0"
        const val testRunner = "1.1.0"
        const val espresso = "3.4.0"
        const val testExtensions = "1.1.1"
        const val testRules = "1.1.0"
        const val truth = "1.1.3"
        const val coroutineTest = "1.6.4"
        const val fragmentTesting = "1.5.4"
        const val hiltTesting = BuildPlugins.Versions.hilt
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val arcCore = "androidx.arch.core:core-testing:${Versions.arcCore}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espressoAccessibility = "androidx.test.espresso:espresso-accessibility:${Versions.espresso}"
    const val espressoWeb = "androidx.test.espresso:espresso-web:${Versions.espresso}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentTesting}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtensions}"
    const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltTesting}"
}

object DevLibraries {
    private object Versions {
        const val leakCanary = "2.9.1"
    }

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}