plugins {
    id(ScriptPlugins.infrastructure)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath (BuildPlugins.androidGradlePlugin)
        classpath (BuildPlugins.kotlinGradlePlugin)
        classpath (BuildPlugins.safeArgsGradlePlugin)
        classpath (BuildPlugins.hiltGradlePlugin)
        classpath (BuildPlugins.jacoco)
        classpath (BuildPlugins.secretsGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}