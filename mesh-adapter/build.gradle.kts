plugins {
    id("goreecloud.kotlin.library")
}

dependencies {
    api(project(":platform-core"))
    testImplementation(kotlin("test"))
}
