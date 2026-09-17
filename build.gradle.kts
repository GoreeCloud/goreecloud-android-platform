plugins {
    base
}

tasks.register("platformCheck") {
    group = "verification"
    description = "Runs the GoreeCloud Android Platform verification suite."
    dependsOn(
        ":platform-core:test",
        ":identity-adapter:test",
        ":mesh-adapter:test",
        ":policy-adapter:test",
        ":testing:test",
        ":android-core:testDebugUnitTest",
        ":glaze-ui:testDebugUnitTest",
    )
}

tasks.named("check") {
    dependsOn("platformCheck")
}
