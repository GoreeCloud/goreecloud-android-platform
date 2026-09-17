plugins {
    id("goreecloud.android.library")
}

android {
    namespace = "com.goreecloud.android.platform.runtime"
}

dependencies {
    api(project(":platform-core"))
    testImplementation(kotlin("test"))
}
