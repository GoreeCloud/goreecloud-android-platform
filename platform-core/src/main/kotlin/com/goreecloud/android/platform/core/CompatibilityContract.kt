package com.goreecloud.android.platform.core

/**
 * Inclusive/exclusive semantic-version requirement for GoreeCloud Android Platform consumers.
 *
 * A null [maximumExclusive] means the requirement has no declared upper bound. Callers should
 * only omit an upper bound when the owning compatibility policy intentionally permits it.
 */
data class VersionRequirement(
    val minimumInclusive: PlatformVersion,
    val maximumExclusive: PlatformVersion? = null,
) {
    init {
        require(maximumExclusive == null || minimumInclusive < maximumExclusive) {
            "Maximum platform version must be greater than the minimum platform version."
        }
    }

    operator fun contains(version: PlatformVersion): Boolean =
        version >= minimumInclusive && (maximumExclusive == null || version < maximumExclusive)
}

/**
 * Inclusive Android API-level requirement.
 */
data class AndroidApiRequirement(
    val minimumInclusive: Int,
    val maximumInclusive: Int? = null,
) {
    init {
        require(minimumInclusive > 0) { "Minimum Android API level must be positive." }
        require(maximumInclusive == null || maximumInclusive >= minimumInclusive) {
            "Maximum Android API level must be greater than or equal to the minimum Android API level."
        }
    }

    operator fun contains(apiLevel: Int): Boolean =
        apiLevel >= minimumInclusive && (maximumInclusive == null || apiLevel <= maximumInclusive)
}

enum class CompatibilityVerdict {
    COMPATIBLE,
    INCOMPATIBLE,
    INDETERMINATE,
}

data class CompatibilityAssessment(
    val verdict: CompatibilityVerdict,
    val reasons: List<String>,
) {
    init {
        require(reasons.isNotEmpty()) { "Compatibility assessments must explain their result." }
    }
}

data class ConsumerEnvironment(
    val platformVersion: PlatformVersion? = null,
    val androidApiLevel: Int? = null,
)

/**
 * Versioned compatibility-contract model owned by this repository.
 *
 * The model deliberately separates platform compatibility from producer-system protocol
 * compatibility. Identity, Mesh, Policy, Privacy Shield, Wardveil Security, Everkeep, Manager,
 * Observability, and other producer contract versions remain authoritative in their owning systems.
 */
data class AndroidPlatformCompatibilityContract(
    val contractVersion: PlatformVersion,
    val platformRequirement: VersionRequirement,
    val androidApiRequirement: AndroidApiRequirement? = null,
) {
    fun evaluate(environment: ConsumerEnvironment): CompatibilityAssessment {
        val reasons = mutableListOf<String>()
        var incompatible = false
        var indeterminate = false

        val platformVersion = environment.platformVersion
        if (platformVersion == null) {
            indeterminate = true
            reasons += "Platform version is unknown."
        } else if (platformVersion !in platformRequirement) {
            incompatible = true
            reasons += "Platform version $platformVersion does not satisfy the declared platform requirement."
        }

        androidApiRequirement?.let { requirement ->
            val apiLevel = environment.androidApiLevel
            if (apiLevel == null) {
                indeterminate = true
                reasons += "Android API level is unknown."
            } else if (apiLevel !in requirement) {
                incompatible = true
                reasons += "Android API level $apiLevel does not satisfy the declared Android API requirement."
            }
        }

        return when {
            incompatible -> CompatibilityAssessment(
                verdict = CompatibilityVerdict.INCOMPATIBLE,
                reasons = reasons,
            )

            indeterminate -> CompatibilityAssessment(
                verdict = CompatibilityVerdict.INDETERMINATE,
                reasons = reasons,
            )

            else -> CompatibilityAssessment(
                verdict = CompatibilityVerdict.COMPATIBLE,
                reasons = listOf("All declared compatibility requirements are satisfied."),
            )
        }
    }
}
