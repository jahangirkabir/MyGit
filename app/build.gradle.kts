plugins {
    // Application Specific Plugins
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.androidHilt)
    id(BuildPlugins.safeargs)
    id(BuildPlugins.secretsGradle)

    // Internal Script plugins
    id(ScriptPlugins.variants)
    id(ScriptPlugins.quality)
    id(ScriptPlugins.compilation)
}

hilt {
    enableAggregatingTask = true
}

android {
    namespace = "com.jahanbabu.mygit"

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        compileSdk = AndroidSdk.compile
        applicationId = AndroidClient.appId
        versionCode = AndroidClient.versionCode
        versionName = AndroidClient.versionName
        testInstrumentationRunner = AndroidClient.testRunner
    }

    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }

        //TODO: Remove this when migrating the DI framework
        getByName("main") { java.srcDir("$buildDir/generated/source/kapt/main") }
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    //Compile time dependencies
    kapt(Libraries.lifecycleCompiler)
    kapt(Libraries.hiltCompiler)
//    kapt(Libraries.hiltXCompiler)
    kapt(Libraries.moshiKotlinCodegen)
    kaptAndroidTest(Libraries.hiltCompiler)
    // Application dependencies
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.viewModel)
    implementation(Libraries.liveData)
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.material)
    implementation(Libraries.fragment)
    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigationUi)
    implementation(Libraries.androidAnnotations)
    implementation(Libraries.dataStore)
    implementation(Libraries.paging)
    implementation(Libraries.glide)
    implementation(Libraries.glideTransformations)
    implementation(Libraries.hilt)
    implementation(Libraries.hiltNavigationFragment)
    implementation(Libraries.retrofit)
    implementation(Libraries.okHttpLoggingInterceptor)
    implementation(Libraries.moshi)
    implementation(Libraries.moshiKotlin)
    implementation(Libraries.moshiConverter)
    // Unit/Android tests dependencies
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.arcCore)
    testImplementation(TestLibraries.coroutineTest)

    // Acceptance tests dependencies
    debugImplementation(TestLibraries.fragmentTesting)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espressoCore)
//    androidTestImplementation(TestLibraries.espressoIntents)
//    androidTestImplementation(TestLibraries.espressoContrib)
//    androidTestImplementation(TestLibraries.espressoAccessibility)
//    androidTestImplementation(TestLibraries.espressoWeb)
//    androidTestImplementation(TestLibraries.truth)
    androidTestImplementation(TestLibraries.testExtJunit)
    androidTestImplementation(TestLibraries.testRules)
    androidTestImplementation(TestLibraries.hiltTesting)

    // Development dependencies
    debugImplementation(DevLibraries.leakCanary)
}
