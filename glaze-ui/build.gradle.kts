plugins {
    id("goreecloud.android.compose")
}

android {
    namespace = "com.goreecloud.android.glaze"
}

dependencies {
    api(project(":platform-core"))
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    testImplementation(kotlin("test"))
}
