plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

group = "com.goreecloud.android"
version = providers.gradleProperty("goreecloud.version").orElse("0.1.0-SNAPSHOT").get()

android {
    compileSdk = 36

    defaultConfig {
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    packaging {
        resources {
            excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }
}

kotlin {
    jvmToolchain(17)
}
