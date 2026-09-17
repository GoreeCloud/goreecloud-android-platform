package com.goreecloud.android.platform.runtime

import android.os.Build
import com.goreecloud.android.platform.core.AndroidPlatformCompatibilityContract
import com.goreecloud.android.platform.core.CompatibilityAssessment
import com.goreecloud.android.platform.core.ConsumerEnvironment
import com.goreecloud.android.platform.core.PlatformVersion

data class AndroidRuntimeSnapshot(
    val sdkInt: Int,
    val previewSdkInt: Int,
) {
    init {
        require(sdkInt > 0) { "Android SDK level must be positive." }
        require(previewSdkInt >= 0) { "Android preview SDK level must not be negative." }
    }

    val isPreviewBuild: Boolean
        get() = previewSdkInt > 0

    fun isAtLeast(apiLevel: Int): Boolean = sdkInt >= apiLevel

    /**
     * Builds the repository-owned compatibility environment from verified local runtime facts.
     *
     * The Android API fact comes directly from [Build.VERSION.SDK_INT]. The platform version is
     * supplied by the caller because this module does not infer artifact or release identity from
     * a branch, package name, or build configuration. Preview builds deliberately keep their
     * reported SDK_INT rather than being promoted to an unreleased final API level.
     */
    fun asConsumerEnvironment(
        platformVersion: PlatformVersion? = null,
    ): ConsumerEnvironment = ConsumerEnvironment(
        platformVersion = platformVersion,
        androidApiLevel = sdkInt,
    )

    fun evaluateCompatibility(
        contract: AndroidPlatformCompatibilityContract,
        platformVersion: PlatformVersion? = null,
    ): CompatibilityAssessment = contract.evaluate(
        asConsumerEnvironment(platformVersion = platformVersion),
    )
}

object AndroidRuntime {
    fun snapshot(): AndroidRuntimeSnapshot = AndroidRuntimeSnapshot(
        sdkInt = Build.VERSION.SDK_INT,
        previewSdkInt = Build.VERSION.PREVIEW_SDK_INT,
    )
}
