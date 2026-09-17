pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "goreecloud-android-platform"

include(
    ":platform-core",
    ":android-core",
    ":identity-adapter",
    ":mesh-adapter",
    ":policy-adapter",
    ":glaze-ui",
    ":testing",
)
