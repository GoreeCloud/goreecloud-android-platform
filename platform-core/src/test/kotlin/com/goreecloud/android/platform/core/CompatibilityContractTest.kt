package com.goreecloud.android.platform.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CompatibilityContractTest {
    private val contract = AndroidPlatformCompatibilityContract(
        contractVersion = PlatformVersion(0, 1, 0),
        platformRequirement = VersionRequirement(
            minimumInclusive = PlatformVersion(0, 1, 0),
            maximumExclusive = PlatformVersion(0, 2, 0),
        ),
        androidApiRequirement = AndroidApiRequirement(
            minimumInclusive = 29,
            maximumInclusive = 36,
        ),
    )

    @Test
    fun compatibleWhenAllDeclaredRequirementsAreSatisfied() {
        val assessment = contract.evaluate(
            ConsumerEnvironment(
                platformVersion = PlatformVersion(0, 1, 0),
                androidApiLevel = 36,
            ),
        )

        assertEquals(CompatibilityVerdict.COMPATIBLE, assessment.verdict)
        assertEquals(
            listOf("All declared compatibility requirements are satisfied."),
            assessment.reasons,
        )
    }

    @Test
    fun incompatibleWhenPlatformVersionFallsOutsideDeclaredLine() {
        val assessment = contract.evaluate(
            ConsumerEnvironment(
                platformVersion = PlatformVersion(0, 2, 0),
                androidApiLevel = 36,
            ),
        )

        assertEquals(CompatibilityVerdict.INCOMPATIBLE, assessment.verdict)
        assertTrue(assessment.reasons.any { it.contains("Platform version 0.2.0") })
    }

    @Test
    fun incompatibleWhenAndroidApiLevelFallsOutsideDeclaredRange() {
        val assessment = contract.evaluate(
            ConsumerEnvironment(
                platformVersion = PlatformVersion(0, 1, 0),
                androidApiLevel = 28,
            ),
        )

        assertEquals(CompatibilityVerdict.INCOMPATIBLE, assessment.verdict)
        assertTrue(assessment.reasons.any { it.contains("Android API level 28") })
    }

    @Test
    fun indeterminateWhenRequiredConsumerFactsAreUnknown() {
        val assessment = contract.evaluate(
            ConsumerEnvironment(
                platformVersion = null,
                androidApiLevel = null,
            ),
        )

        assertEquals(CompatibilityVerdict.INDETERMINATE, assessment.verdict)
        assertEquals(2, assessment.reasons.size)
    }

    @Test
    fun optionalAndroidRequirementDoesNotInventApiCompatibility() {
        val platformOnlyContract = AndroidPlatformCompatibilityContract(
            contractVersion = PlatformVersion(0, 1, 0),
            platformRequirement = VersionRequirement(
                minimumInclusive = PlatformVersion(0, 1, 0),
                maximumExclusive = PlatformVersion(0, 2, 0),
            ),
        )

        val assessment = platformOnlyContract.evaluate(
            ConsumerEnvironment(platformVersion = PlatformVersion(0, 1, 0)),
        )

        assertEquals(CompatibilityVerdict.COMPATIBLE, assessment.verdict)
    }

    @Test
    fun invalidRangesAreRejected() {
        assertFailsWith<IllegalArgumentException> {
            VersionRequirement(
                minimumInclusive = PlatformVersion(1, 0, 0),
                maximumExclusive = PlatformVersion(1, 0, 0),
            )
        }

        assertFailsWith<IllegalArgumentException> {
            AndroidApiRequirement(minimumInclusive = 36, maximumInclusive = 29)
        }
    }
}
