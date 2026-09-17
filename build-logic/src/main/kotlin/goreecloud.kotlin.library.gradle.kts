plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "com.goreecloud.android"
version = providers.gradleProperty("goreecloud.version").orElse("0.1.0-SNAPSHOT").get()

kotlin {
    jvmToolchain(17)
}

tasks.withType<Test>().configureEach {
    testLogging {
        events("failed", "skipped")
    }
}
