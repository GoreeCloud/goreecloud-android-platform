plugins {
    id("goreecloud.kotlin.library")
}

dependencies {
    api(project(":platform-core"))
    api(project(":identity-adapter"))
    api(project(":mesh-adapter"))
    api(project(":policy-adapter"))
    testImplementation(kotlin("test"))
}
